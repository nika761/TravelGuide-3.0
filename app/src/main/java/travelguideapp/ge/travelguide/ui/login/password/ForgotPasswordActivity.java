package travelguideapp.ge.travelguide.ui.login.password;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.request.ForgotPasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;


public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordListener, ChangePasswordFragment.ChangePasswordListener {
    private EditText eEmail;
    private TextView emailHead, forgotPassword, forgotPasswordBody, backBtn;
    private Button send;
    private ConstraintLayout changePasswordContainer;
    private ScrollView forgotPasswordContainer;
    private String email;
    private ForgotPasswordPresenter presenter;
    private FrameLayout loaderContainer;
    private LottieAnimationView loader;

    private ChangePasswordFragment changePasswordFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        try {
            if (getIntent().getStringExtra("request_for").equals("change")) {
                changePasswordFragment = ChangePasswordFragment.getInstance(this);
                HelperUI.loadFragment(changePasswordFragment, null, R.id.change_password_container, false, true, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {
        presenter = new ForgotPasswordPresenter(this);

        backBtn = findViewById(R.id.forgot_psw_back);
        backBtn.setOnClickListener(v -> finish());

        forgotPassword = findViewById(R.id.forgot_password_forgot_password);
        forgotPasswordBody = findViewById(R.id.forgot_password_offer_body);

        loaderContainer = findViewById(R.id.forgot_password_loader_container);
        loader = findViewById(R.id.forgot_password_loader);

        changePasswordContainer = findViewById(R.id.change_password_container);
        forgotPasswordContainer = findViewById(R.id.forgot_password_container);

        eEmail = findViewById(R.id.forgot_password_email);
        eEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        emailHead = findViewById(R.id.forgot_password_email_head);

        send = findViewById(R.id.forgot_password_save_btn);
        send.setOnClickListener(v -> {
            send.setClickable(false);
            email = HelperUI.checkEditTextData(eEmail, emailHead, getString(R.string.email), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, eEmail.getContext());

            if (email != null && HelperUI.isEmailValid(email)) {
                HelperUI.setBackgroundDefault(eEmail, emailHead, getString(R.string.email), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
                presenter.forgotPassword(new ForgotPasswordRequest(email, GlobalPreferences.getLanguageId(this)));
                eEmail.clearFocus();
                loadingVisibility(true);
            } else {
                send.setClickable(true);
                HelperUI.setBackgroundWarning(eEmail, emailHead, getString(R.string.email), eEmail.getContext());
            }
        });

    }

    @Override
    public void onForgetPassword(ForgotPasswordResponse forgotPasswordResponse) {
        if (forgotPasswordResponse != null) {
            loadingVisibility(false);
            Toast.makeText(this, forgotPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChangePassword(ChangePasswordResponse changePasswordResponse) {
        loadingVisibility(false);
        send.setClickable(true);
        MyToaster.getToast(this, changePasswordResponse.getMessage());
        if (changePasswordResponse.getStatus() == 0) {
            finish();
        } else {
            setChangePasswordFragmentSaveBtn();
        }
    }

    private void setChangePasswordFragmentSaveBtn() {
        try {
            changePasswordFragment.setSaveBtn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        try {
            setChangePasswordFragmentSaveBtn();
            loadingVisibility(false);
            send.setClickable(true);
            MyToaster.getToast(this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadingVisibility(boolean visible) {
        if (visible) {
            loaderContainer.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
        } else {
            loaderContainer.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPasswordChoose(ChangePasswordRequest changePasswordRequest) {
        loadingVisibility(true);
        presenter.changePassword(GlobalPreferences.getAccessToken(this), changePasswordRequest);
    }

}
