package travelguideapp.ge.travelguide.ui.login.password;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.request.ResetPasswordRequest;
import travelguideapp.ge.travelguide.model.response.ResetPasswordResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.upload.UploadPostActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordListener, View.OnFocusChangeListener {
    private EditText eEmail, ePassword, eConfirmPassword;
    private String email, password, confirmPassword, token;
    private TextView emailHead, passwordHead, confirmPasswordHead;
    private Button save;
    private ResetPasswordPresenter resetPasswordPresenter;

    private FrameLayout loaderContainer;
    private LottieAnimationView loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initUI();
        verifyToken();
    }


    private void initUI() {
        resetPasswordPresenter = new ResetPasswordPresenter(this);


        loaderContainer = findViewById(R.id.reset_password_loader_container);
        loader = findViewById(R.id.reset_password_loader);

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

            email = HelperUI.checkEditTextData(eEmail, emailHead, getString(R.string.email), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            password = HelperUI.checkEditTextData(ePassword, passwordHead, getString(R.string.password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            confirmPassword = HelperUI.checkEditTextData(eConfirmPassword, confirmPasswordHead, getString(R.string.confirm_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            if (email != null && HelperUI.checkEmail(email)) {
                HelperUI.setBackgroundDefault(eEmail, emailHead, getString(R.string.email), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(eEmail, emailHead, getString(R.string.email), save.getContext());
            }

            if (password != null && HelperUI.checkPassword(password)) {
                HelperUI.setBackgroundDefault(ePassword, passwordHead, getString(R.string.password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(ePassword, passwordHead, getString(R.string.password), save.getContext());
            }

            if (password != null && confirmPassword != null && HelperUI.checkConfirmPassword(password, confirmPassword)) {
                HelperUI.setBackgroundDefault(eConfirmPassword, confirmPasswordHead, getString(R.string.confirm_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            } else {
                HelperUI.setBackgroundWarning(eConfirmPassword, confirmPasswordHead, getString(R.string.confirm_password), save.getContext());
            }

            if (email != null && password != null && confirmPassword != null) {
                loadingVisibility(true);
                resetPasswordPresenter.resetPassword(new ResetPasswordRequest(email, token, password, confirmPassword, GlobalPreferences.getLanguageId(this)));
            }

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        });

    }

    private void verifyToken() {
        try {
            Uri uri = getIntent().getData();
            if (uri != null) {
                List<String> params = uri.getPathSegments();

                String token = params.get(params.size() - 1);

                if (token != null) {
                    this.token = token;
//                    Log.e("token", token);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            MyToaster.getErrorToaster(this, "Try Again");
            finish();
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
    public void onPasswordReset(ResetPasswordResponse resetPasswordResponse) {
        if (resetPasswordResponse != null) {
            if (resetPasswordResponse.getStatus() == 0) {
                GlobalPreferences.saveAccessToken(this, resetPasswordResponse.getAccess_token());
                GlobalPreferences.saveUserId(this, resetPasswordResponse.getUser().getId());
                GlobalPreferences.saveUserRole(this, resetPasswordResponse.getUser().getRole());
                GlobalPreferences.saveLoginType(this, GlobalPreferences.TRAVEL_GUIDE);

                loadingVisibility(false);

                Intent intent = new Intent(this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("password_changed", "password_changed");
                startActivity(intent);
                Toast.makeText(this, resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPasswordResetError(String message) {
        loadingVisibility(false);
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
