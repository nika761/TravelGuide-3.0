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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.activity.SignInActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.interfaces.FragmentClickActions;
import com.example.travelguide.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.presenters.SignInPresenter;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.Objects;

public class SignInFragment extends Fragment implements ISignInFragment {

    private FragmentClickActions fragmentClickActions;
    private SignInPresenter signInPresenter;
    private TextView registerTxt, signInTxt, cancelSignInTxt, enterMailHead, enterPasswordHead, forgotPassword, notHaveAccout, connectWiht;
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

    public SignInFragment() {

    }

    public SignInFragment(FragmentClickActions fragmentClickActions) {
        this.fragmentClickActions = fragmentClickActions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initGoogleSignClient();
        setClickListeners();
    }

//    private void checkCurrentFragment() {
//        fragmentClickActions.backToSignInFragment();
//    }

    private void initUI(View view) {
        signInPresenter = new SignInPresenter(this);
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
    }

    private void initGoogleSignClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
    }

    private void setClickListeners() {

        registerTxt.setOnClickListener(v -> fragmentClickActions.registerBtnClicked());

        signInBtn.setOnClickListener(v -> {
            if (enterEmail.getText().toString().isEmpty()) {
                enterEmail.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
                enterMailHead.setText("*" + getString(R.string.email_or_phone_number));
                enterMailHead.setTextColor(getResources().getColor(R.color.red));
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(enterEmail);
            }

            if (enterPassword.getText().toString().isEmpty()) {
                enterPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
                enterPasswordHead.setText("*" + getString(R.string.password));
                enterPasswordHead.setTextColor(getResources().getColor(R.color.red));
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(enterPassword);
            } else {
                Intent intent = new Intent(getContext(), UserPageActivity.class);
                startActivity(intent);
            }
        });


        terms.setOnClickListener((View view) -> {
            ((SignInActivity) (context)).loadTermsFragment();
        });

        googleBtn.setOnClickListener(v -> {
            signInWithGoogle();
        });

        forgotPassword.setOnClickListener(v -> ((SignInActivity) Objects.requireNonNull(getActivity())).loadForgotPswFragment());

        facebookBtn.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
            signBtnFacebook.performClick();
        });

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
                Log.d("fbShame", error.toString());
            }
        });
    }

    private void startUserActivity(User user) {

        Intent intent = new Intent(getActivity(), UserPageActivity.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("lastName", user.getLastName());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("url", user.getUrl());
        intent.putExtra("id", user.getId());
        intent.putExtra("loginType", user.getLoginType());
        startActivity(intent);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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


    private void keyboardFocusHelper() {
        enterEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
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

}
