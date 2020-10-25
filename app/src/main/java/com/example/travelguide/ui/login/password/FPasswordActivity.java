package com.example.travelguide.ui.login.password;

import android.content.Context;
import android.os.Bundle;
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
import com.example.travelguide.model.request.ForgotPasswordRequest;
import com.example.travelguide.model.response.ForgotPasswordResponse;

public class FPasswordActivity extends AppCompatActivity implements FPasswordListener {
    private EditText eEmail;
    private TextView emailHead;
    private String email;
    private FPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUI();
    }

    private void initUI() {
        forgotPasswordPresenter = new FPasswordPresenter(this);

        TextView backBtn = findViewById(R.id.forgot_psw_back);
        backBtn.setOnClickListener(v -> finish());

        eEmail = findViewById(R.id.forgot_password_email);
        eEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        emailHead = findViewById(R.id.forgot_password_email_head);

        Button save = findViewById(R.id.forgot_password_save_btn);
        save.setOnClickListener(v -> {
            email = HelperUI.checkEditTextData(eEmail, emailHead, "Email",
                    HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, eEmail.getContext());

            if (email != null && HelperUI.checkEmail(email)) {
                HelperUI.setBackgroundDefault(eEmail, emailHead, "Email",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
                forgotPasswordPresenter.forgotPassword(new ForgotPasswordRequest(email, HelperPref.getLanguageId(this)));
                eEmail.clearFocus();
            } else {
                HelperUI.setBackgroundWarning(eEmail, emailHead, "Email", eEmail.getContext());
            }
        });
    }

    @Override
    public void onGetForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse) {
        if (forgotPasswordResponse != null) {
            Toast.makeText(this, forgotPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetForgotPasswordError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (forgotPasswordPresenter != null) {
            forgotPasswordPresenter = null;
        }
        super.onDestroy();
    }
}
