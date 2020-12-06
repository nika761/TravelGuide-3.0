package com.travel.guide.ui.login.signIn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.travel.guide.R;
import com.travel.guide.helper.ClientManager;
import com.travel.guide.helper.language.GlobalLanguages;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.model.request.LoginRequest;
import com.travel.guide.model.request.VerifyEmailRequest;
import com.travel.guide.model.response.AppSettingsResponse;
import com.travel.guide.model.response.AuthWithFirebaseResponse;
import com.travel.guide.model.response.LoginResponse;
import com.travel.guide.model.response.VerifyEmailResponse;
import com.travel.guide.ui.home.HomePageActivity;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.ui.login.signUp.SignUpFireBaseActivity;
import com.travel.guide.ui.login.password.ForgotPasswordActivity;
import com.travel.guide.ui.login.signUp.SignUpActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;

import static com.travel.guide.enums.LoadWebViewType.POLICY;
import static com.travel.guide.enums.LoadWebViewType.TERMS;
import static com.travel.guide.helper.HelperUI.loadAnimation;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class SignInActivity extends AppCompatActivity implements SignInListener {

    private SignInPresenter signInPresenter;
    private CallbackManager callbackManager;

    private LottieAnimationView animationView;
    private FrameLayout frameLayout;
    private TextView enterMailHead;
    private TextView enterPasswordHead;
    private EditText enterEmail, enterPassword;
    private LoginButton signBtnFacebook;
    private ImageButton passwordStateBtn;

    private final static int GOOGLE_SIGN_IN = 0;
    private int platformId;
    private boolean passwordState;
    private String key, name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInPresenter = new SignInPresenter(this);

        verifyEmail();

        initUI();

    }

    private void verifyEmail() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();

            String id = params.get(params.size() - 2);
            String signature = uri.getQueryParameter("signature");

            if (signature != null && id != null) {
                signInPresenter.verify(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(this), new VerifyEmailRequest(id, signature));
                Log.e("email", signature + " " + id);
            }
        }
    }

    private void initUI() {
        try {

            GlobalLanguages currentLanguage = GlobalPreferences.getCurrentLanguage(this);

            animationView = findViewById(R.id.animation_view_sign);
            frameLayout = findViewById(R.id.frame_sign_loader);
            signBtnFacebook = findViewById(R.id.login_button_facebook);
            callbackManager = CallbackManager.Factory.create();

            enterMailHead = findViewById(R.id.enter_mail_head);
//            enterMailHead.setText(currentLanguage.getEmail_field_head());

            enterPasswordHead = findViewById(R.id.enter_password_head);
//            enterPasswordHead.setText(currentLanguage.getPassword_field_head());

            enterEmail = findViewById(R.id.enter_email);
            enterEmail.setOnFocusChangeListener(this::onFocusChange);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterEmail.setFocusedByDefault(false);
            }

            enterPassword = findViewById(R.id.enter_password);
            enterPassword.setOnFocusChangeListener(this::onFocusChange);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPassword.setFocusedByDefault(false);
            }

            TextView notHaveAccount = findViewById(R.id.not_have_account);
            TextView connectWith = findViewById(R.id.connect_with);
            ImageView lineLeft = findViewById(R.id.line_left);
            ImageView lineRight = findViewById(R.id.line_right);
            LinearLayout terms = findViewById(R.id.linear_terms);

            TextView registerTxt = findViewById(R.id.register_now);
//            registerTxt.setText(globalLanguages.getSign_up());
            registerTxt.setOnClickListener(this::onViewClick);

            TextView forgotPassword = findViewById(R.id.forgot_password_sign_in);
//            forgotPassword.setText(globalLanguages.getForgot_password());
            forgotPassword.setOnClickListener(this::onViewClick);

            TextView termsOfServices = findViewById(R.id.terms_of_services);
//            termsOfServices.setText(globalLanguages.getTerms_of_services());
            termsOfServices.setOnClickListener(this::onViewClick);

            TextView privacyPolicy = findViewById(R.id.privacy_policy);
//            termsOfServices.setText(globalLanguages.getPrivacy_policy());
            privacyPolicy.setOnClickListener(this::onViewClick);

            SignInButton signBtnGoogle = findViewById(R.id.sign_in_button_google);
            signBtnGoogle.setSize(SignInButton.SIZE_ICON_ONLY);

            Button googleBtn = findViewById(R.id.google);
            googleBtn.setOnClickListener(this::onViewClick);

            Button signInBtn = findViewById(R.id.sign_in_button_main);
//            signInBtn.setText(globalLanguages.getSign_in());
            signInBtn.setOnClickListener(this::onViewClick);

            Button facebookBtn = findViewById(R.id.facebook);
            facebookBtn.setOnClickListener(this::onViewClick);

            passwordStateBtn = findViewById(R.id.password_visibility);
            passwordStateBtn.setOnClickListener(this::onViewClick);

            loadAnimation(enterMailHead, R.anim.anim_swipe_bottom, 0);
            loadAnimation(enterEmail, R.anim.anim_swipe_bottom, 50);
            loadAnimation(enterPasswordHead, R.anim.anim_swipe_bottom, 100);
            loadAnimation(enterPassword, R.anim.anim_swipe_bottom, 150);
            loadAnimation(passwordStateBtn, R.anim.anim_swipe_bottom, 150);
            loadAnimation(forgotPassword, R.anim.anim_swipe_bottom, 200);
            loadAnimation(notHaveAccount, R.anim.anim_swipe_bottom, 250);
            loadAnimation(registerTxt, R.anim.anim_swipe_bottom, 250);
            loadAnimation(signInBtn, R.anim.anim_swipe_bottom, 300);
            loadAnimation(lineLeft, R.anim.anim_swipe_left, 400);
            loadAnimation(lineRight, R.anim.anim_swipe_right, 400);
            loadAnimation(connectWith, R.anim.anim_swipe_bottom, 350);
            loadAnimation(facebookBtn, R.anim.anim_swipe_left, 450);
            loadAnimation(googleBtn, R.anim.anim_swipe_right, 450);
            loadAnimation(terms, R.anim.anim_swipe_bottom, 500);

        } catch (Exception e) {
            Intent signIntent = new Intent(this, SignInActivity.class);
            startActivity(signIntent);
            e.printStackTrace();
        }


    }

    private void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.password_visibility:
                if (passwordState) {
                    passwordStateBtn.setBackground(getResources().getDrawable(R.drawable.ic_password_hide, null));
                    enterPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordState = false;
                } else {
                    passwordStateBtn.setBackground(getResources().getDrawable(R.drawable.ic_password_show, null));
                    enterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordState = true;
                }
                break;

            case R.id.register_now:
                onFocusChange(v, false);
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.terms_of_services:
                HelperUI.startWebActivity(this, TERMS);
                break;

            case R.id.privacy_policy:
                HelperUI.startWebActivity(this, POLICY);
                break;

            case R.id.forgot_password_sign_in:
                onFocusChange(v, false);
                Intent intent1 = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent1);
                break;

            case R.id.google:
                signInWithGoogle();
                break;

            case R.id.facebook:
                signInWithFacebook();
                break;

            case R.id.sign_in_button_main:
                onFocusChange(v, false);
                signInWithAccount();
                break;
        }
    }

    private void signInWithAccount() {

        int white = getResources().getColor(R.color.white, null);

        String email = HelperUI.checkEditTextData(enterEmail, enterMailHead, "Email or Phone Number", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, this);

        String password = HelperUI.checkEditTextData(enterPassword, enterPasswordHead, "Password", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, this);

        if (email != null) {
            HelperUI.setBackgroundDefault(enterEmail, enterMailHead, "Email or Phone Number", white, HelperUI.BACKGROUND_DEF_WHITE);
            if (password != null && HelperUI.checkPassword(password)) {
                HelperUI.setBackgroundDefault(enterPassword, enterPasswordHead, "Password", white, HelperUI.BACKGROUND_DEF_WHITE);
                showLoading(true);
                signInPresenter.singIn(new LoginRequest(email, password));
            } else {
                HelperUI.setBackgroundWarning(enterPassword, enterPasswordHead, "Password", this);
            }
        } else {
            HelperUI.setBackgroundWarning(enterEmail, enterMailHead, "Email or Phone Number", this);
        }
    }

    private void signInWithGoogle() {

        GlobalPreferences.saveLoginType(this, GlobalPreferences.GOOGLE);

        Intent signInIntent = ClientManager.googleSignInClient(this).getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);

    }

    private void signInWithFacebook() {
        GlobalPreferences.saveLoginType(this, GlobalPreferences.FACEBOOK);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
        signBtnFacebook.performClick();
        signBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInPresenter.authWithFb(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignInActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoading(boolean show) {
        if (show) {
            frameLayout.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
            animationView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onVerify(VerifyEmailResponse verifyEmailResponse) {

        GlobalPreferences.saveAccessToken(this, verifyEmailResponse.getAccess_token());
        GlobalPreferences.saveUserId(this, verifyEmailResponse.getUser().getId());
        GlobalPreferences.saveUserRole(this, verifyEmailResponse.getUser().getRole());
        GlobalPreferences.saveLoginType(this, GlobalPreferences.TRAVEL_GUIDE);

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onError(String message) {

        showLoading(false);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse) {
        GlobalPreferences.saveAccessToken(this, authWithFirebaseResponse.getAccess_token());
        GlobalPreferences.saveUserId(this, authWithFirebaseResponse.getUser().getId());
        GlobalPreferences.saveUserRole(this, authWithFirebaseResponse.getUser().getRole());

        showLoading(false);

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFireBaseSignUp(String token, int platform, String name) {
        this.key = token;
        this.platformId = platform;
        this.name = name;
    }

    @Override
    public void onFireBaseAuthSignUp() {
        Intent intent = new Intent(this, SignUpFireBaseActivity.class);
        intent.putExtra("platform", platformId);
        intent.putExtra("key", key);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void onGetSettings(AppSettingsResponse.App_settings appSettings) {

    }

    @Override
    public void onSign(LoginResponse loginResponse) {
        switch (loginResponse.getStatus()) {
            case 0:

                GlobalPreferences.saveUser(this, loginResponse.getUser());
                GlobalPreferences.saveAccessToken(this, loginResponse.getAccess_token());
                GlobalPreferences.saveUserId(this, loginResponse.getUser().getId());
                GlobalPreferences.saveUserRole(this, loginResponse.getUser().getRole());
                GlobalPreferences.saveLoginType(this, GlobalPreferences.TRAVEL_GUIDE);

                showLoading(false);

                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                finish();

                break;

            case 1:
                Toast.makeText(this, String.valueOf(loginResponse.getStatus()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {

            showLoading(true);

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInPresenter.authWithGoogle(task);
        }

    }

//    private void loadAnimation(View target, int animationId, int offset) {
//        Animation animation = AnimationUtils.loadAnimation(this, animationId);
//        animation.setStartOffset(offset);
//        target.startAnimation(animation);
//    }

    @Override
    protected void onDestroy() {
        if (signInPresenter != null) {
            signInPresenter = null;
        }
        super.onDestroy();
    }

}
