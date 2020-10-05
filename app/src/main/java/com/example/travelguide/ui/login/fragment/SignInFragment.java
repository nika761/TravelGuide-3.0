package com.example.travelguide.ui.login.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;
import com.example.travelguide.model.UserModel;
import com.example.travelguide.model.response.AppSettingsResponse;
import com.example.travelguide.model.response.AuthWithFirebaseResponse;
import com.example.travelguide.ui.login.activity.DateActivity;
import com.example.travelguide.ui.login.activity.ForgotPasswordActivity;
import com.example.travelguide.ui.login.activity.SignUpActivity;
import com.example.travelguide.ui.login.activity.SignInActivity;
import com.example.travelguide.ui.home.HomePageActivity;
import com.example.travelguide.ui.login.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.model.request.LoginRequest;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.ui.login.presenter.SignInPresenter;
import com.example.travelguide.helper.HelperClients;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.helper.HelperUI;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class SignInFragment extends Fragment implements ISignInFragment {

    private SignInPresenter signInPresenter;
    private TextView registerTxt, signInTxt, cancelSignInTxt, enterMailHead, enterPasswordHead, forgotPassword, notHaveAccout, connectWiht, termsOfServices, privacyPolicy;
    private EditText enterEmail, enterPassword;
    private Button facebookBtn, googleBtn;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton signBtnFacebook;
    private Button signInBtn;
    private int GOOGLE_SIGN_IN = 0;
    private Context context;
    private LinearLayout terms;
    private ImageView lineLeft, lineRight;
    private String email, password;
    private DatabaseReference myRef;
    private String key, name;
    private int platformId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        signInPresenter = new SignInPresenter(this);

        SignInButton signBtnGoogle = view.findViewById(R.id.sign_in_button_google);
        signBtnGoogle.setSize(SignInButton.SIZE_ICON_ONLY);

        googleBtn = view.findViewById(R.id.google);
        googleBtn.setOnClickListener(this::onViewClick);


        signBtnFacebook = view.findViewById(R.id.login_button_facebook);
        facebookBtn = view.findViewById(R.id.facebook);
        facebookBtn.setOnClickListener(this::onViewClick);
        callbackManager = CallbackManager.Factory.create();


        registerTxt = view.findViewById(R.id.register_now);
        registerTxt.setOnClickListener(this::onViewClick);

        forgotPassword = view.findViewById(R.id.forgot_password_sign_in);
        forgotPassword.setOnClickListener(this::onViewClick);

//        signInTxt = view.findViewById(R.id.sign_in_text_view);
        signInBtn = view.findViewById(R.id.sign_in_button_main);
        signInBtn.setOnClickListener(this::onViewClick);

        enterEmail = view.findViewById(R.id.enter_email);
        enterEmail.setOnFocusChangeListener(this::onFocusChange);

        enterPassword = view.findViewById(R.id.enter_password);
        enterPassword.setOnFocusChangeListener(this::onFocusChange);

        enterMailHead = view.findViewById(R.id.enter_mail_head);
        enterPasswordHead = view.findViewById(R.id.enter_password_head);
//        cancelSignInTxt = view.findViewById(R.id.cancel_text_view);

        notHaveAccout = view.findViewById(R.id.not_have_account);
        connectWiht = view.findViewById(R.id.connect_with);
        lineLeft = view.findViewById(R.id.line_left);
        lineRight = view.findViewById(R.id.line_right);
        terms = view.findViewById(R.id.linear_terms);

        termsOfServices = view.findViewById(R.id.terms_of_services);
        termsOfServices.setOnClickListener(this::onViewClick);

        privacyPolicy = view.findViewById(R.id.privacy_policy);
        privacyPolicy.setOnClickListener(this::onViewClick);

    }

    private void signInWithAccount() {
        email = HelperUI.checkEditTextData(enterEmail, enterMailHead, "Email or Phone Number", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, context);

        password = HelperUI.checkEditTextData(enterPassword, enterPasswordHead, "Password", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE, context);

        if (email != null) {
            HelperUI.setBackgroundDefault(enterEmail, enterMailHead, "Email or Phone Number", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE);
            if (password != null && HelperUI.checkPassword(password)) {
                HelperUI.setBackgroundDefault(enterPassword, enterPasswordHead, "Password", HelperUI.WHITE, HelperUI.BACKGROUND_DEF_WHITE);
                ((SignInActivity) context).startLoader();
                signInPresenter.singIn(new LoginRequest(email, password));
            } else {
                HelperUI.setBackgroundWarning(enterPassword, enterPasswordHead, "Password", context);
            }
        } else {
            HelperUI.setBackgroundWarning(enterEmail, enterMailHead, "Email or Phone Number", context);
        }
    }

    private void signInWithGoogle() {

        HelperPref.saveCurrentPlatform(context, HelperPref.GOOGLE);
        Intent signInIntent = HelperClients.googleSignInClient(context).getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);


    }

    private void signInWithFacebook() {
        HelperPref.saveCurrentPlatform(context, HelperPref.FACEBOOK);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
        signBtnFacebook.performClick();
        signBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInPresenter.authWithFb(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("fbShame", error.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInPresenter.authWithGoogle(task);
        }

    }

    private void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onAuthError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse) {
        HelperPref.saveAccessToken(context, authWithFirebaseResponse.getAccess_token());
        HelperPref.saveCurrentUserId(context, authWithFirebaseResponse.getUser().getId());

        Intent intent = new Intent(context, HomePageActivity.class);
//                intent.putExtra("server_user", loggedUser);

        ((SignInActivity) context).stopLoader();

        context.startActivity(intent);

        ((SignInActivity) context).finish();
    }

    @Override
    public void onFireBaseSignUp(String token, int platform, String name) {
        this.key = token;
        this.platformId = platform;
        this.name = name;
    }

    @Override
    public void onFireBaseAuthSignUp() {
        Intent intent = new Intent(context, DateActivity.class);
        intent.putExtra("platform", platformId);
        intent.putExtra("key", key);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    public void onGetSettings(AppSettingsResponse.App_settings appSettings) {

    }

    @Override
    public void onGetSettingsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSign(LoginResponse loginResponse) {
        switch (loginResponse.getStatus()) {
            case 0:

                HelperPref.saveAccessToken(context, loginResponse.getAccess_token());
                HelperPref.saveUser(context, loginResponse.getUser());
                HelperPref.saveCurrentUserId(context, loginResponse.getUser().getId());
                HelperPref.saveCurrentPlatform(context, HelperPref.TRAVEL_GUIDE);

                Intent intent = new Intent(context, HomePageActivity.class);
//                intent.putExtra("server_user", loggedUser);

                ((SignInActivity) context).stopLoader();

                context.startActivity(intent);

                ((SignInActivity) context).finish();
                break;

            case 1:
                Toast.makeText(context, String.valueOf(loginResponse.getStatus()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (signInPresenter != null) {
            signInPresenter = null;
        }
        if (context != null) {
            context = null;
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadAnimation(enterMailHead, R.anim.anim_swipe_bottom, 0);
        loadAnimation(enterEmail, R.anim.anim_swipe_bottom, 50);
        loadAnimation(enterPasswordHead, R.anim.anim_swipe_bottom, 100);
        loadAnimation(enterPassword, R.anim.anim_swipe_bottom, 150);
        loadAnimation(forgotPassword, R.anim.anim_swipe_bottom, 200);
        loadAnimation(notHaveAccout, R.anim.anim_swipe_bottom, 250);
        loadAnimation(registerTxt, R.anim.anim_swipe_bottom, 250);
        loadAnimation(signInBtn, R.anim.anim_swipe_bottom, 300);
        loadAnimation(lineLeft, R.anim.anim_swipe_left, 400);
        loadAnimation(lineRight, R.anim.anim_swipe_right, 400);
        loadAnimation(connectWiht, R.anim.anim_swipe_bottom, 350);
        loadAnimation(facebookBtn, R.anim.anim_swipe_left, 450);
        loadAnimation(googleBtn, R.anim.anim_swipe_right, 450);
        loadAnimation(terms, R.anim.anim_swipe_bottom, 500);

    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.register_now:
                onFocusChange(v, false);
                Intent intent = new Intent(context, SignUpActivity.class);
                context.startActivity(intent);
                break;

            case R.id.terms_of_services:
                HelperUI.startTermsAndPolicyActivity(context, HelperUI.TERMS);
                break;

            case R.id.privacy_policy:
                HelperUI.startTermsAndPolicyActivity(context, HelperUI.POLICY);
                break;

            case R.id.forgot_password_sign_in:
                onFocusChange(v, false);
                Intent intent1 = new Intent(context, ForgotPasswordActivity.class);
                context.startActivity(intent1);
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
}
