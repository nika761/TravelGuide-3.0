package com.travel.guide.ui.login.password;

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
import com.travel.guide.R;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.model.request.ChangePasswordRequest;
import com.travel.guide.model.request.ForgotPasswordRequest;
import com.travel.guide.model.response.ChangePasswordResponse;
import com.travel.guide.model.response.ForgotPasswordResponse;
import com.travel.guide.utility.GlobalPreferences;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordListener, ChangePasswordFragment.ChangePasswordListener {
    private EditText eEmail;
    private TextView emailHead;
    private ConstraintLayout changePasswordContainer;
    private ScrollView forgotPasswordContainer;
    private String email;
    private ForgotPasswordPresenter presenter;
    private FrameLayout loaderContainer;
    private LottieAnimationView loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        try {
            if (getIntent().getStringExtra("request_for").equals("change")) {
                HelperUI.loadFragment(ChangePasswordFragment.getInstance(this), null, R.id.change_password_container, false, true, this);
                ChangePasswordFragment.getInstance(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {
        presenter = new ForgotPasswordPresenter(this);

        TextView backBtn = findViewById(R.id.forgot_psw_back);
        backBtn.setOnClickListener(v -> finish());

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

        Button save = findViewById(R.id.forgot_password_save_btn);
        save.setOnClickListener(v -> {
            email = HelperUI.checkEditTextData(eEmail, emailHead, "Email", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, eEmail.getContext());

            if (email != null && HelperUI.checkEmail(email)) {
                HelperUI.setBackgroundDefault(eEmail, emailHead, "Email", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
                presenter.forgotPassword(new ForgotPasswordRequest(email, GlobalPreferences.getLanguageId(this)));
                eEmail.clearFocus();
                loadingVisibility(true);
            } else {
                HelperUI.setBackgroundWarning(eEmail, emailHead, "Email", eEmail.getContext());
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
        Toast.makeText(this, changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
        if (changePasswordResponse.getStatus() == 0)
            finish();
    }

    @Override
    public void onError(String message) {
        loadingVisibility(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        presenter.changePassword(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(this), changePasswordRequest);
    }
}
