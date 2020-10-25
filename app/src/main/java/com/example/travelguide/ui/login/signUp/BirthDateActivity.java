package com.example.travelguide.ui.login.signUp;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.helper.HelperUI;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.request.SignUpWithFirebaseRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;
import com.example.travelguide.ui.home.HomePageActivity;

import java.util.Calendar;
import java.util.Objects;

public class BirthDateActivity extends AppCompatActivity implements BirthDateListener {

    private TextView dateHead, nickHead, dateOfBirth, nickError, nickOffer1, nickOffer2;
    private EditText eNickName;
    private Button save;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private BirthDatePresenter birthDatePresenter;
    private String nick1, nick2, key, name, nickName;
    private long startTime;
    private int platformId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        this.platformId = getIntent().getIntExtra("platform", 0);
        this.key = getIntent().getStringExtra("key");
        this.name = getIntent().getStringExtra("name");

        birthDatePresenter = new BirthDatePresenter(this);
        initUI();
        setClickListeners();
    }

    private void initUI() {
        dateHead = findViewById(R.id.enter_date_of_birth_head);
        dateOfBirth = findViewById(R.id.enter_birth_date);
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
        nickOffer2 = findViewById(R.id.enter_nick_2);

        save = findViewById(R.id.enter_info_save);
    }

    void setClickListeners() {

        save.setOnClickListener(v -> {
//            if (dateOfBirth.getText().toString().isEmpty()) {
//                dateOfBirth.setBackground(getResources().getDrawable(R.drawable.bg_fields_warning));
//                dateHead.setText("* Birth Date ");
//                dateHead.setTextColor(getResources().getColor(R.color.red));
//                YoYo.with(Techniques.Shake)
//                        .duration(300)
//                        .playOn(dateOfBirth);
//            } else {
//                dateOfBirth.setBackground(getResources().getDrawable(R.drawable.bg_signup_fields));
//                dateHead.setText(" Birth Date ");
//                dateHead.setTextColor(getResources().getColor(R.color.black));
//                birthDate = dateOfBirth.getText().toString();
//            }

            nickName = HelperUI.checkEditTextData(eNickName, nickHead, getString(R.string.nick_name), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, eNickName.getContext());
            if (nickName != null) {
                int languageId = HelperPref.getLanguageId(this);
                birthDatePresenter.signUpWithFirebase(new SignUpWithFirebaseRequest(key, nickName, String.valueOf(startTime), languageId, platformId));

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        nickOffer1.setOnClickListener(v -> eNickName.setText(nick1));

        nickOffer2.setOnClickListener(v -> eNickName.setText(nick2));

        dateOfBirth.setOnClickListener(v -> showDatePickerDialog());

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
                HelperPref.saveCurrentPlatform(this, HelperPref.FACEBOOK);
                break;
            case 2:
                HelperPref.saveCurrentPlatform(this, HelperPref.GOOGLE);
                break;
        }
        HelperPref.saveAccessToken(this, signUpWithFirebaseResponse.getAccess_token());
        HelperPref.saveCurrentUserId(this, signUpWithFirebaseResponse.getUser().getId());

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onError(String message) {
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
    }

    @Override
    public void checkNickName() {
        birthDatePresenter.checkNick(new CheckNickRequest(nickName, name, null));
    }

}
