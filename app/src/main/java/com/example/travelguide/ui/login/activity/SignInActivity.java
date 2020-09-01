package com.example.travelguide.ui.login.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.VerifyEmailRequest;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.model.response.VerifyEmailResponse;
import com.example.travelguide.ui.home.activity.UserPageActivity;
import com.example.travelguide.ui.login.fragment.SignInFragment;
import com.example.travelguide.helper.HelperUI;
import com.example.travelguide.ui.login.interfaces.OnVerify;
import com.example.travelguide.ui.login.presenter.SignInActivityPresenter;

import java.util.List;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class SignInActivity extends AppCompatActivity implements OnVerify {

    private LottieAnimationView animationView;
    private FrameLayout frameLayout;
    private SignInActivityPresenter signInActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInActivityPresenter = new SignInActivityPresenter(this);
        verifyEmail();
        initUI();
        HelperUI.loadFragment(new SignInFragment(), null, R.id.fragment_container, false, this);
    }

    private void verifyEmail() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();

            String id = params.get(params.size() - 1);
            String signature = uri.getQueryParameter("signature");

            if (signature != null && id != null) {
                signInActivityPresenter.verify(ACCESS_TOKEN_BEARER +
                        HelperPref.getCurrentAccessToken(this), new VerifyEmailRequest(id, signature));
                Log.e("email", signature + " " + id);
            }

        } else {
            Toast.makeText(this, "Link Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void initUI() {
        animationView = findViewById(R.id.animation_view_sign);
        frameLayout = findViewById(R.id.frame_sign_loader);
    }

    public void startLoader() {
        frameLayout.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
    }

    public void stopLoader() {
        frameLayout.setVisibility(View.GONE);
        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (signInActivityPresenter != null) {
            signInActivityPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onVerify(VerifyEmailResponse verifyEmailResponse) {

//        HelperPref.saveServerUser(this, verifyEmailResponse.getUser());

        Intent intent = new Intent(this, UserPageActivity.class);
        startActivity(intent);
        this.finish();

        HelperPref.saveAccessToken(this, verifyEmailResponse.getAccess_token());
        Toast.makeText(this, "Signed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerifyError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
