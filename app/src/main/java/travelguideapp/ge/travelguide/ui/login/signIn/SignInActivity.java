package travelguideapp.ge.travelguide.ui.login.signIn;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
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
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.language.GlobalLanguages;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.LoginRequest;
import travelguideapp.ge.travelguide.model.request.VerifyEmailRequest;
import travelguideapp.ge.travelguide.model.response.AuthWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.LoginResponse;
import travelguideapp.ge.travelguide.model.response.VerifyEmailResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.login.signUp.SignUpFireBaseActivity;
import travelguideapp.ge.travelguide.ui.login.password.ForgotPasswordActivity;
import travelguideapp.ge.travelguide.ui.login.signUp.SignUpActivity;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import travelguideapp.ge.travelguide.enums.LoadWebViewBy;

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

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "travelguideapp.ge.travelguide",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        }
//        catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

    }

    private void verifyEmail() {
        try {
            Uri uri = getIntent().getData();
            if (uri != null) {
                List<String> params = uri.getPathSegments();

                String id = params.get(params.size() - 2);
                String signature = uri.getQueryParameter("signature");

                if (signature != null && id != null) {
                    signInPresenter.verify(GlobalPreferences.getAccessToken(this), new VerifyEmailRequest(id, signature));
                    Log.e("email", signature + " " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initUI() {
        try {

            GlobalLanguages currentLanguage = GlobalPreferences.getCurrentLanguage(this);

            animationView = findViewById(R.id.animation_view_sign);
            frameLayout = findViewById(R.id.frame_sign_loader);
            signBtnFacebook = findViewById(R.id.login_button_facebook);
            callbackManager = CallbackManager.Factory.create();
            Log.e("facebookError", "callback for manager created");

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

            HelperUI.loadAnimation(enterMailHead, R.anim.anim_swipe_bottom, 0);
            HelperUI.loadAnimation(enterEmail, R.anim.anim_swipe_bottom, 50);
            HelperUI.loadAnimation(enterPasswordHead, R.anim.anim_swipe_bottom, 100);
            HelperUI.loadAnimation(enterPassword, R.anim.anim_swipe_bottom, 150);
            HelperUI.loadAnimation(passwordStateBtn, R.anim.anim_swipe_bottom, 150);
            HelperUI.loadAnimation(forgotPassword, R.anim.anim_swipe_bottom, 200);
            HelperUI.loadAnimation(notHaveAccount, R.anim.anim_swipe_bottom, 250);
            HelperUI.loadAnimation(registerTxt, R.anim.anim_swipe_bottom, 250);
            HelperUI.loadAnimation(signInBtn, R.anim.anim_swipe_bottom, 300);
            HelperUI.loadAnimation(lineLeft, R.anim.anim_swipe_left, 400);
            HelperUI.loadAnimation(lineRight, R.anim.anim_swipe_right, 400);
            HelperUI.loadAnimation(connectWith, R.anim.anim_swipe_bottom, 350);
            HelperUI.loadAnimation(facebookBtn, R.anim.anim_swipe_left, 450);
            HelperUI.loadAnimation(googleBtn, R.anim.anim_swipe_right, 450);
            HelperUI.loadAnimation(terms, R.anim.anim_swipe_bottom, 500);

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
                HelperUI.startWebActivity(this, LoadWebViewBy.TERMS, "");
                break;

            case R.id.privacy_policy:
                HelperUI.startWebActivity(this, LoadWebViewBy.POLICY, "");
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

        String email = HelperUI.checkEditTextData(enterEmail, enterMailHead, getString(R.string.email_or_phone_number), HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, this);

        String password = HelperUI.checkEditTextData(enterPassword, enterPasswordHead, getString(R.string.password), HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, this);

        if (email != null) {
            HelperUI.setBackgroundDefault(enterEmail, enterMailHead, getString(R.string.email_or_phone_number), white, HelperUI.BACKGROUND_DEF_WHITE);
            if (password != null && HelperUI.checkPassword(password)) {
                HelperUI.setBackgroundDefault(enterPassword, enterPasswordHead, getString(R.string.password), white, HelperUI.BACKGROUND_DEF_WHITE);
                showLoading(true);
                signInPresenter.singIn(new LoginRequest(email, password));
            } else {
                HelperUI.setBackgroundWarning(enterPassword, enterPasswordHead, getString(R.string.password), this);
            }
        } else {
            HelperUI.setBackgroundWarning(enterEmail, enterMailHead, getString(R.string.email_or_phone_number), this);
        }
    }

    private void signInWithGoogle() {

        GlobalPreferences.saveLoginType(this, GlobalPreferences.GOOGLE);

        Intent signInIntent = ClientManager.googleSignInClient(this).getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);

    }

    private void signInWithFacebook() {
        Log.e("facebookError", "method started from head UI");
        GlobalPreferences.saveLoginType(this, GlobalPreferences.FACEBOOK);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
//        signBtnFacebook.performClick();
//        signBtnFacebook.callOnClick();
        signBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("facebookError", "method started from UI");
                showLoading(true);
                signInPresenter.authWithFb(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
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
        try {
            showLoading(false);
            MyToaster.getErrorToaster(this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Log.e("facebookError", "callback for manager UI");
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
