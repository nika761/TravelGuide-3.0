package com.example.travelguide.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.activity.FacebookActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.interfaces.FragmentClickActions;
import com.example.travelguide.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.presenters.SignInFragmentPresenter;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class SignInFragment extends Fragment implements ISignInFragment {

    private FragmentClickActions fragmentClickActions;
    private SignInFragmentPresenter signInFragmentPresenter;
    private TextView registerTxt, signInTxt, cancelSignInTxt, enterMailHead, enterPasswordHead;
    private EditText enterEmail, enterPassword;
    private SignInButton signBtnGoogle;
    private ImageView facebookBtn, googleBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton signBtnFacebook;
    private Button signInBtn;
    private int RC_SIGN_IN = 0;

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
        signInFragmentPresenter = new SignInFragmentPresenter(this);
        signBtnGoogle = view.findViewById(R.id.sign_in_button_google);
        signBtnGoogle.setSize(SignInButton.SIZE_ICON_ONLY);
        googleBtn = view.findViewById(R.id.google);
        registerTxt = view.findViewById(R.id.register_now);
        signInTxt = view.findViewById(R.id.sign_in_text_view);
        signInBtn = view.findViewById(R.id.sign_in_button_main);
        enterEmail = view.findViewById(R.id.enter_email);
        enterPassword = view.findViewById(R.id.enter_password);
        enterMailHead = view.findViewById(R.id.enter_mail_head);
        enterPasswordHead = view.findViewById(R.id.enter_password_head);
        cancelSignInTxt = view.findViewById(R.id.cancel_text_view);
        facebookBtn = view.findViewById(R.id.facebook);
        signBtnFacebook = view.findViewById(R.id.login_button_facebook);
        callbackManager = CallbackManager.Factory.create();
    }

    private void initGoogleSignClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
    }

    private void setClickListeners() {

        registerTxt.setOnClickListener(v -> fragmentClickActions.registerBtnClicked());

//        signInBtn.setOnClickListener(v -> {
//            if (enterEmail.getText().toString().isEmpty()) {
//                enterEmail.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
//                enterMailHead.setText("*" + getString(R.string.email_or_phone_number));
//                enterMailHead.setTextColor(getResources().getColor(R.color.red));
//                YoYo.with(Techniques.Shake)
//                        .duration(300)
//                        .playOn(enterEmail);
//            }
//
//            if (enterPassword.getText().toString().isEmpty()) {
//                enterPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
//                enterPasswordHead.setText("*" + getString(R.string.password));
//                enterPasswordHead.setTextColor(getResources().getColor(R.color.red));
//                YoYo.with(Techniques.Shake)
//                        .duration(300)
//                        .playOn(enterPassword);
//            } else {
//                Intent intent = new Intent(getContext(), UserPageActivity.class);
//                startActivity(intent);
//            }
//        });

        googleBtn.setOnClickListener(v -> {
            signInWithGoogle();
        });

        facebookBtn.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
            signBtnFacebook.performClick();
        });

        callbackManager = CallbackManager.Factory.create();
        signBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInFragmentPresenter.fetchFbUserData(loginResult.getAccessToken());
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

        String firstName = user.getName();
        String lastName = user.getLastName();
        String url = user.getUrl();
        String id = user.getId();
        String email = user.getEmail();
        String loginType = user.getLoginType();

        Intent intent = new Intent(getActivity(), UserPageActivity.class);
        intent.putExtra("name", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("email", email);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
        intent.putExtra("loginType", loginType);
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
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInFragmentPresenter.fetchGglUserData(task);
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
        if (signInFragmentPresenter != null) {
            signInFragmentPresenter = null;
        }
        super.onDestroy();
    }
}
