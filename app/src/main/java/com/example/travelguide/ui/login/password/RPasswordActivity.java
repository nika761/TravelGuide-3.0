package com.example.travelguide.ui.login.password;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.travelguide.model.request.ResetPasswordRequest;
import com.example.travelguide.model.response.ResetPasswordResponse;
import com.example.travelguide.ui.home.HomePageActivity;

import java.util.List;

public class RPasswordActivity extends AppCompatActivity implements RPasswordListener, View.OnFocusChangeListener {
    private EditText eEmail, ePassword, eConfirmPassword;
    private String email, password, confirmPassword, token;
    private TextView emailHead, passwordHead, confirmPasswordHead;
    private Button save;
    private RPasswordPresenter resetPasswordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initUI();
        verifyToken();
    }


    private void initUI() {
        resetPasswordPresenter = new RPasswordPresenter(this);

        eEmail = findViewById(R.id.reset_password_email);
        eEmail.setOnFocusChangeListener(this);
        emailHead = findViewById(R.id.reset_password_email_head);

        ePassword = findViewById(R.id.reset_password);
        ePassword.setOnFocusChangeListener(this);
        passwordHead = findViewById(R.id.reset_password_head);

        eConfirmPassword = findViewById(R.id.reset_password_confirm);
        eConfirmPassword.setOnFocusChangeListener(this);
        confirmPasswordHead = findViewById(R.id.reset_password_confirm_head);

        save = findViewById(R.id.reset_password_save);
        save.setOnClickListener(v -> {

            email = HelperUI.checkEditTextData(eEmail, emailHead, "Email",
                    HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            password = HelperUI.checkEditTextData(ePassword, passwordHead, "Password",
                    HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            confirmPassword = HelperUI.checkEditTextData(eConfirmPassword, confirmPasswordHead, "Confirm Password",
                    HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            if (email != null && HelperUI.checkEmail(email)) {
                HelperUI.setBackgroundDefault(eEmail, emailHead, "Email",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(eEmail, emailHead, "Email", save.getContext());
            }

            if (password != null && HelperUI.checkPassword(password)) {
                HelperUI.setBackgroundDefault(ePassword, passwordHead, "Password",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(ePassword, passwordHead, "Password", save.getContext());
            }

            if (password != null && confirmPassword != null && HelperUI.checkConfirmPassword(password, confirmPassword)) {
                HelperUI.setBackgroundDefault(eConfirmPassword, confirmPasswordHead, "Confirm Password",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(eConfirmPassword, confirmPasswordHead, "Confirm Password", save.getContext());
            }

            if (email != null && password != null && confirmPassword != null) {
                resetPasswordPresenter.resetPassword(new ResetPasswordRequest(email, token,
                        password, confirmPassword, HelperPref.getLanguageId(this)));
            }

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        });

    }

    private void verifyToken() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();

            String token = params.get(params.size() - 1);

            if (token != null) {
                this.token = token;
            }

        } else {
            Toast.makeText(this, "Token Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPasswordReset(ResetPasswordResponse resetPasswordResponse) {
        if (resetPasswordResponse != null) {
            if (resetPasswordResponse.getStatus() == 0) {
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.putExtra("password_changed", "password_changed");
                startActivity(intent);
                Toast.makeText(this, resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPasswordResetError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (resetPasswordPresenter != null) {
            resetPasswordPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
