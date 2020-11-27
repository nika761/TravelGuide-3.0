package com.travel.guide.ui.login.signUp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.helper.customView.HelperUI;
import com.travel.guide.model.request.CheckNickRequest;
import com.travel.guide.model.request.SignUpWithFirebaseRequest;
import com.travel.guide.model.response.CheckNickResponse;
import com.travel.guide.model.response.SignUpWithFirebaseResponse;
import com.travel.guide.ui.home.HomePageActivity;

import java.util.Calendar;
import java.util.Objects;

public class SignUpFireBaseActivity extends AppCompatActivity implements SignUpFireBaseListener {

    private TextView dateHead, nickHead, dateOfBirth, nickError, nickOffer1, nickOffer2;
    private LottieAnimationView loader;
    private EditText eNickName;
    private Button save;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private SignUpFireBasePresenter presenter;

    private String nick1, nick2, key, name, nickName;
    private long startTime;
    private int platformId;
    private int gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_firebase);

        this.platformId = getIntent().getIntExtra("platform", 0);
        this.key = getIntent().getStringExtra("key");
        this.name = getIntent().getStringExtra("name");

        initUI();
    }

    private void initUI() {

        presenter = new SignUpFireBasePresenter(this);

        dateHead = findViewById(R.id.enter_date_of_birth_head);

        dateOfBirth = findViewById(R.id.enter_birth_date);
        dateOfBirth.setOnClickListener(v -> showDatePickerDialog());

        nickHead = findViewById(R.id.enter_nickName_head);
        loader = findViewById(R.id.fire_loading);

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
        nickOffer1.setOnClickListener(v -> eNickName.setText(nick1));

        nickOffer2 = findViewById(R.id.enter_nick_2);
        nickOffer2.setOnClickListener(v -> eNickName.setText(nick2));

        save = findViewById(R.id.enter_info_save);
        save.setOnClickListener(v -> {

            nickName = HelperUI.checkEditTextData(eNickName, nickHead, getString(R.string.nick_name), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, this);
            if (nickName != null) {

                nickError.setVisibility(View.GONE);
                nickOffer1.setVisibility(View.GONE);
                nickOffer2.setVisibility(View.GONE);

                loader.setVisibility(View.VISIBLE);

                int languageId = HelperPref.getLanguageId(SignUpFireBaseActivity.this);
                presenter.signUpWithFirebase(new SignUpWithFirebaseRequest(key, nickName, String.valueOf(startTime), languageId, platformId, gender));

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d("dates", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

            String dayString = day < 10 ? "0" + day : String.valueOf(day);
            String monthString = month < 10 ? "0" + month : String.valueOf(month);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

//            Date currentDate = new Date();
//            int age = currentDate.getYear() - year;

            int age = currentYear - year;

            if (age < 13) {
                Toast.makeText(save.getContext(), "Application age restriction 13+", Toast.LENGTH_SHORT).show();
            } else {

                String date = year + "/" + monthString + "/" + dayString;
                dateOfBirth.setText(date);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                startTime = calendar.getTimeInMillis();
                Log.e("datetime", String.valueOf(startTime));
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

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse) {
        switch (platformId) {
            case 1:
                HelperPref.saveLoginType(this, HelperPref.FACEBOOK);
                break;
            case 2:
                HelperPref.saveLoginType(this, HelperPref.GOOGLE);
                break;
        }

        HelperPref.saveUserId(this, signUpWithFirebaseResponse.getUser().getId());
        HelperPref.saveUserRole(this, signUpWithFirebaseResponse.getUser().getRole());
        HelperPref.saveAccessToken(this, signUpWithFirebaseResponse.getAccess_token());

        loader.setVisibility(View.GONE);

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetNickCheckResult(CheckNickResponse checkNickResponse) {
        nickError.setVisibility(View.VISIBLE);
        nickOffer1.setVisibility(View.VISIBLE);
        nickOffer2.setVisibility(View.VISIBLE);

        nick1 = checkNickResponse.getNicknames_to_offer().get(0);
        nickOffer1.setText(nick1);

        nick2 = checkNickResponse.getNicknames_to_offer().get(1);
        nickOffer2.setText(nick2);

        loader.setVisibility(View.GONE);
    }

    @Override
    public void checkNickName() {
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
