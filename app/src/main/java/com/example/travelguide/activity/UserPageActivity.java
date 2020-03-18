package com.example.travelguide.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

public class UserPageActivity extends AppCompatActivity {

    private TextView firstNameContainer, lastNameContainer, emailContainer;
    private Button signOutBtn;
    private ImageView userPhotoContainer;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        initUI();
        initGoogleSignClient();
        setClickListeners();
        ongGetUserDate();
    }

    private void ongGetUserDate() {
        String firstName = getIntent().getStringExtra("name");
        String lastName = getIntent().getStringExtra("lastName");
        String url = getIntent().getStringExtra("url");
        String id = getIntent().getStringExtra("id");
        String email = getIntent().getStringExtra("email");
        String loginType = getIntent().getStringExtra("loginType");

        User user = new User(firstName, lastName, url, id, email, loginType);
        Utils.saveUser(this, user);

        firstNameContainer.setText(firstName);
        lastNameContainer.setText(lastName);
        emailContainer.setText(email);
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(userPhotoContainer);
        }
    }

    private void initUI() {
        firstNameContainer = findViewById(R.id.user_name_container);
        lastNameContainer = findViewById(R.id.user_lastName_container);
        emailContainer = findViewById(R.id.user_email_container);
        userPhotoContainer = findViewById(R.id.user_photo_container);
        signOutBtn = findViewById(R.id.sign_out_button);
    }


    private void setClickListeners() {
        signOutBtn.setOnClickListener(v -> {
            switch (v.getId()) {
                // ...
                case R.id.sign_out_button:
                    signOutFromGoogleAccount();
                    break;
                // ...
            }
        });
    }

    private void initGoogleSignClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(this), gso);
    }

    private void signOutFromGoogleAccount() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    finish();
                });
    }

}
