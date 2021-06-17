package travelguideapp.ge.travelguide.ui.login.signUp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.AuthorizationManager;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperDate;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.customModel.AuthModel;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.request.SignUpWithFirebaseRequest;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.SignUpWithFirebaseResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;

public class SignUpFireBaseActivity extends BaseActivity implements SignUpFireBaseListener {

    private TextView dateHead, nickHead, dateOfBirth, nickError, nickOffer1, nickOffer2;
    private LottieAnimationView loader;
    private FrameLayout loaderContainer;
    private EditText eNickName;
    private Button save;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private SignUpFireBasePresenter presenter;

    private String nick1, nick2, key, name, nickName;
    private long dateInMillis = 0;
    private int platformId;
    private int gender = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_firebase);
        checkFlow();
    }

    private void checkFlow() {
        if (getExtras()) {
            initUI();
        } else {
            finish();
        }
    }

    private boolean getExtras() {
        try {
            this.platformId = getIntent().getIntExtra("platform", 0);
            this.key = getIntent().getStringExtra("key");
            this.name = getIntent().getStringExtra("name");
            return platformId != 0 && key != null && name != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initUI() {

        presenter = new SignUpFireBasePresenter(this);

        dateHead = findViewById(R.id.enter_date_of_birth_head);

        loader = findViewById(R.id.firebase_loader);
        loaderContainer = findViewById(R.id.firebase_loader_container);

        dateOfBirth = findViewById(R.id.enter_birth_date);
        dateOfBirth.setOnClickListener(v -> DialogManager.datePickerDialog(this, mDateSetListener, 0));

        nickHead = findViewById(R.id.enter_nickName_head);

        eNickName = findViewById(R.id.enter_nickName);
        eNickName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        nickError = findViewById(R.id.enter_nick_error);

        nickOffer1 = findViewById(R.id.enter_nick_1);
        nickOffer1.setOnClickListener(v -> setNickName(1));

        nickOffer2 = findViewById(R.id.enter_nick_2);
        nickOffer2.setOnClickListener(v -> setNickName(2));

        save = findViewById(R.id.enter_info_save);
        save.setOnClickListener(v -> {
            onSaveAction();
            closeKeyBoard(v);
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            try {
                if (HelperDate.checkAgeOfUser(year, month + 1, day)) {
                    dateOfBirth.setText(HelperDate.getDateStringFormat(year, month, day));
                    dateInMillis = HelperDate.getDateInMilliFromDate(year, month, day);
                } else
                    MyToaster.showToast(this, getString(R.string.age_restriction_warning));
            } catch (Exception e) {
                MyToaster.showUnknownErrorToast(this);
                e.printStackTrace();
            }
        };


        RadioGroup genderGroup = findViewById(R.id.fire_radio_group);
        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.fire_radio_male:
                    gender = 0;
                    break;

                case R.id.fire_radio_female:
                    gender = 1;
                    break;

                case R.id.fire_radio_other:
                    gender = 2;
                    break;

            }
        });

    }

    private void setNickName(int wich) {
        switch (wich) {
            case 1:
                eNickName.setText(nick1);
                eNickName.setSelection(eNickName.length());
                break;
            case 2:
                eNickName.setText(nick2);
                eNickName.setSelection(eNickName.length());
                break;
        }
    }

    private void onSaveAction() {

        if (isNullOrEmpty(eNickName.getText().toString())) {
            nickName = null;
            HelperUI.inputWarning(this, eNickName, nickHead, false);
        } else {
            nickName = eNickName.getText().toString();
            HelperUI.inputDefault(this, eNickName, nickHead, false);
        }

        if (dateInMillis == 0) {
            HelperUI.inputWarning(this, dateOfBirth, dateHead, false);
        } else {
            HelperUI.inputDefault(this, dateOfBirth, dateHead, false);
        }

        if (gender == 4) {
            MyToaster.showToast(this, getString(R.string.gender_restriction_warning));
            return;
        }

        if (nickName != null && dateInMillis != 0) {
            updateUI(false);
            presenter.signUpWithFirebase(new SignUpWithFirebaseRequest(key, nickName, String.valueOf(dateInMillis), GlobalPreferences.getLanguageId(), platformId, gender));
        }

    }

    private void updateUI(boolean enable) {
        try {
            if (enable) {
                loader.setVisibility(View.GONE);
                loaderContainer.setVisibility(View.GONE);
                save.setClickable(true);
                dateOfBirth.setClickable(true);
                eNickName.setClickable(true);
            } else {
                loader.setVisibility(View.VISIBLE);
                loaderContainer.setVisibility(View.VISIBLE);
                save.setClickable(false);
                dateOfBirth.setClickable(false);
                eNickName.setClickable(false);
                nickError.setVisibility(View.GONE);
                nickOffer1.setVisibility(View.GONE);
                nickOffer2.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse) {
        AuthModel authModel = new AuthModel();
        switch (platformId) {
            case 1:
                authModel.setLoginType(GlobalPreferences.FACEBOOK);
                break;
            case 2:
                authModel.setLoginType(GlobalPreferences.GOOGLE);
                break;
        }

        authModel.setUserId(signUpWithFirebaseResponse.getUser().getId());
        authModel.setUserRole(signUpWithFirebaseResponse.getUser().getRole());
        authModel.setAccessToken(signUpWithFirebaseResponse.getAccess_token());

        AuthorizationManager.persistAuthorizationState(authModel);

        updateUI(true);

        startActivity(HomePageActivity.getRedirectIntent(this));

    }

    @Override
    public void onError(String message) {
        MyToaster.showToast(this, message);
        updateUI(true);
    }

    @Override
    public void onGetNickCheckResult(CheckNickResponse checkNickResponse) {
        try {
            nickError.setVisibility(View.VISIBLE);
            nickOffer1.setVisibility(View.VISIBLE);
            nickOffer2.setVisibility(View.VISIBLE);

            nick1 = checkNickResponse.getNicknames_to_offer().get(0);
            nickOffer1.setText(nick1);

            nick2 = checkNickResponse.getNicknames_to_offer().get(1);
            nickOffer2.setText(nick2);

            updateUI(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void checkNickName() {
        if (presenter != null)
            presenter.checkNick(new CheckNickRequest(nickName, name, null));
    }


    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }
}
