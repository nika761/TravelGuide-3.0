package com.example.travelguide.fragments;

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
import com.example.travelguide.activity.ForgotPasswordActivity;
import com.example.travelguide.activity.SignUpActivity;
import com.example.travelguide.activity.SignInActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.model.request.LoginRequestModel;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.presenters.SignInPresenter;
import com.example.travelguide.utils.UtilsFields;
import com.example.travelguide.utils.UtilsGoogle;
import com.example.travelguide.utils.UtilsPref;
import com.example.travelguide.utils.UtilsTerms;
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
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class SignInFragment extends Fragment implements ISignInFragment {

    private SignInPresenter signInPresenter;
    private TextView registerTxt, signInTxt, cancelSignInTxt, enterMailHead, enterPasswordHead, forgotPassword, notHaveAccout, connectWiht, termsOfServices, privacyPolicy;
    private EditText enterEmail, enterPassword;
    private SignInButton signBtnGoogle;
    private Button facebookBtn, googleBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton signBtnFacebook;
    private Button signInBtn;
    private int RC_SIGN_IN = 0;
    private Context context;
    private LinearLayout terms;
    private ImageView lineLeft, lineRight;
    private String email, password;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setClickListeners();
    }


    private void initUI(View view) {
        signInPresenter = new SignInPresenter(this);
        mGoogleSignInClient = UtilsGoogle.initGoogleSignInClient(context);
        signBtnGoogle = view.findViewById(R.id.sign_in_button_google);
        signBtnGoogle.setSize(SignInButton.SIZE_ICON_ONLY);
        googleBtn = view.findViewById(R.id.google);
        registerTxt = view.findViewById(R.id.register_now);
        forgotPassword = view.findViewById(R.id.forgot_password_sign_in);
//        signInTxt = view.findViewById(R.id.sign_in_text_view);
        signInBtn = view.findViewById(R.id.sign_in_button_main);
        enterEmail = view.findViewById(R.id.enter_email);
        enterPassword = view.findViewById(R.id.enter_password);
        enterMailHead = view.findViewById(R.id.enter_mail_head);
        enterPasswordHead = view.findViewById(R.id.enter_password_head);
//        cancelSignInTxt = view.findViewById(R.id.cancel_text_view);
        facebookBtn = view.findViewById(R.id.facebook);
        signBtnFacebook = view.findViewById(R.id.login_button_facebook);
        callbackManager = CallbackManager.Factory.create();
        notHaveAccout = view.findViewById(R.id.not_have_account);
        connectWiht = view.findViewById(R.id.connect_with);
        lineLeft = view.findViewById(R.id.line_left);
        lineRight = view.findViewById(R.id.line_right);
        terms = view.findViewById(R.id.linear_terms);
        termsOfServices = view.findViewById(R.id.terms_of_services);
        privacyPolicy = view.findViewById(R.id.privacy_policy);
    }

    private void setClickListeners() {

        registerTxt.setOnClickListener(this::onViewClick);

        signInBtn.setOnClickListener(this::onViewClick);

        googleBtn.setOnClickListener(this::onViewClick);

        forgotPassword.setOnClickListener(this::onViewClick);

        facebookBtn.setOnClickListener(this::onViewClick);

        termsOfServices.setOnClickListener(this::onViewClick);

        privacyPolicy.setOnClickListener(this::onViewClick);

        enterEmail.setOnFocusChangeListener(this::onFocusChange);

        enterPassword.setOnFocusChangeListener(this::onFocusChange);

    }

    private void startUserActivity(User user) {
        User currentUser = new User(user.getName(), user.getLastName(), user.getUrl(), user.getId(), user.getEmail(), user.getLoginType());
        UtilsPref.saveUser(context, currentUser);
        Intent intent = new Intent(getActivity(), UserPageActivity.class);
        intent.putExtra("loggedUser", user);
        startActivity(intent);
    }

    private void signInWithAccount() {
        email = UtilsFields.checkEditTextData(enterEmail, enterMailHead, getString(R.string.email_or_phone_number), email, getResources().getColor(R.color.red), getResources().getColor(R.color.white));
        password = UtilsFields.checkEditTextData(enterPassword, enterPasswordHead, getString(R.string.password), password, getResources().getColor(R.color.red), getResources().getColor(R.color.white));
        if (email != null && UtilsFields.checkEmail(email) && password != null && UtilsFields.checkPassword(password)) {
            ((SignInActivity) context).startLoader();
            LoginRequestModel loginRequestModel = new LoginRequestModel(email, password);
            signInPresenter.sentLoginRequest(loginRequestModel);
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
        signBtnFacebook.performClick();
        callbackManager = CallbackManager.Factory.create();
        signBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInPresenter.fetchFbUserData(loginResult.getAccessToken());
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
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInPresenter.fetchGglUserData(task);
            //handleGoogleSignInResult(task);
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
    public void onGetFbUserData(User user) {
        startUserActivity(user);
    }

    @Override
    public void onGetGglUserData(User user) {
        startUserActivity(user);
    }

    @Override
    public void onGetLoginResult(LoginResponseModel loginResponseModel) {
        if (loginResponseModel.getUser() != null) {
            userDataFromServer(loginResponseModel.getUser());
            UtilsPref.saveAccessToken(context, loginResponseModel.getAccess_token());
            Toast.makeText(context, "Signed", Toast.LENGTH_SHORT).show();
        }
    }

    private void userDataFromServer(LoginResponseModel.User userFromServer) {
        UtilsPref.saveServerUser(context, userFromServer);
        Intent intent = new Intent(context, UserPageActivity.class);
        intent.putExtra("server_user", userFromServer);
        ((SignInActivity) context).stopLoader();
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        if (signInPresenter != null) {
            signInPresenter = null;
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

        loadAnimation(enterMailHead, R.anim.swipe_from_bottom_anim, 0);
        loadAnimation(enterEmail, R.anim.swipe_from_bottom_anim, 50);
        loadAnimation(enterPasswordHead, R.anim.swipe_from_bottom_anim, 100);
        loadAnimation(enterPassword, R.anim.swipe_from_bottom_anim, 150);
        loadAnimation(forgotPassword, R.anim.swipe_from_bottom_anim, 200);
        loadAnimation(notHaveAccout, R.anim.swipe_from_bottom_anim, 250);
        loadAnimation(registerTxt, R.anim.swipe_from_bottom_anim, 250);
        loadAnimation(signInBtn, R.anim.swipe_from_bottom_anim, 300);
        loadAnimation(lineLeft, R.anim.swipe_from_left_anim, 400);
        loadAnimation(lineRight, R.anim.swipe_from_right_anim, 400);
        loadAnimation(connectWiht, R.anim.swipe_from_bottom_anim, 350);
        loadAnimation(facebookBtn, R.anim.swipe_from_left_anim, 450);
        loadAnimation(googleBtn, R.anim.swipe_from_right_anim, 450);
        loadAnimation(terms, R.anim.swipe_from_bottom_anim, 500);

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
                UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.TERMS);
                break;

            case R.id.privacy_policy:
                UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.POLICY);
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
