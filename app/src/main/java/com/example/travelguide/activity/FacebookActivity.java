package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FacebookActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView userName, surName;
    private CallbackManager callbackManager;
    private String firstName, lastName, email, id, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        loginButton = findViewById(R.id.login_button_fb);
        userName = findViewById(R.id.user_name_fb);
        surName = findViewById(R.id.surname_fb);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loadFbUserProfile(loginResult.getAccessToken());
                //accessTokenTracker.startTracking();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("fbshame", error.toString());
            }
        });
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            loadFbUserProfile(currentAccessToken);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startUserActivity() {
        Intent intent = new Intent(this, UserPageActivity.class);
        intent.putExtra("name", firstName);
        intent.putExtra("lastName", lastName);
        //intent.putExtra("email", email);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void checkUser() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadFbUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void loadFbUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                if (object != null) {
                    firstName = object.getString("first_name");
                    lastName = object.getString("last_name");
                    id = object.getString("id");
                    url = "https://graph.facebook.com/" + id + "/pictures?type=normal";
//                email = object.getString("email");
                    startUserActivity();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
