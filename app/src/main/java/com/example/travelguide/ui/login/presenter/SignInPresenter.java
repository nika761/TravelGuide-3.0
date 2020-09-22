package com.example.travelguide.ui.login.presenter;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.travelguide.model.FireBaseUserModel;
import com.example.travelguide.model.request.AuthWitFirebaseRequest;
import com.example.travelguide.model.request.LoginRequest;
import com.example.travelguide.model.response.AppSettingsResponse;
import com.example.travelguide.model.response.AuthWithFirebaseResponse;
import com.example.travelguide.ui.login.interfaces.ISignInFragment;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInPresenter {

    private ISignInFragment iSignInFragment;
    private ApiService apiService;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private String key;

    public SignInPresenter(ISignInFragment iSignInFragment) {
        this.iSignInFragment = iSignInFragment;
        apiService = RetrofitManager.getApiService();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public void authWithFb(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getUid();
                    Log.e("tokenssdas", firebaseUser.getUid());

                    firebaseUser.getIdToken(true).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Log.e("tokenssdas", Objects.requireNonNull(Objects.requireNonNull(task1.getResult()).getToken()));
                        }
                    });
                }

                GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
                    try {
                        if (object != null) {
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");
                            String id = object.getString("id");
                            String email = object.getString("email");
                            String url = "https://graph.facebook.com/" + id + "/picture?type=large";

                            key = generateKey();
                            FireBaseUserModel fireBaseUserModel = new FireBaseUserModel(email, firstName, lastName, id, url, key);
                            database.child(id).setValue(fireBaseUserModel);

                            authWithFireBase(new AuthWitFirebaseRequest(key));
                            iSignInFragment.onFireBaseSignUp(key, 1, firstName);

//                            iSignInFragment.onFireBaseAuth(user);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id");
                request.setParameters(parameters);
                request.executeAsync();

                Log.e("facebookError", "signed");
            } else {
                Log.e("facebookError", task.getException().getMessage());
            }
        });
    }

    public void authWithGoogle(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(authResultTask -> {
                    if (authResultTask.isSuccessful()) {

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String personName = account.getDisplayName();
                        String personGivenName = account.getGivenName();
                        String personFamilyName = account.getFamilyName();
                        String personEmail = account.getEmail();
                        String personId = account.getId();
                        String personIdToken = account.getIdToken();
                        String personPhotoUrl;
                        Uri personPhotoUri = account.getPhotoUrl();

                        if (personPhotoUri != null) {
                            personPhotoUrl = personPhotoUri.toString();
                        } else {
                            personPhotoUrl = null;
                        }

                        key = generateKey();
                        FireBaseUserModel fireBaseUserModel = new FireBaseUserModel(personEmail, personGivenName, personFamilyName, personId, personPhotoUrl, key);
                        if (personId != null)
                            database.child(personId).setValue(fireBaseUserModel);

                        iSignInFragment.onFireBaseSignUp(key, 2, personGivenName);

                        authWithFireBase(new AuthWitFirebaseRequest(key));

//                        iSignInFragment.onFireBaseAuth(user);

                        Log.e("googleError", "signed");
                    } else {
                        iSignInFragment.onAuthError(authResultTask.getException().getMessage());
                    }
                });
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            iSignInFragment.onAuthError(e.getLocalizedMessage());
        }
    }

    public void singIn(LoginRequest loginRequest) {
        apiService.signIn(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    iSignInFragment.onSign(response.body());
                } else {
                    iSignInFragment.onAuthError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                iSignInFragment.onAuthError(t.getMessage());
            }
        });
    }

    private void authWithFireBase(AuthWitFirebaseRequest authWitFirebaseRequest) {
        apiService.authWithFirebase(authWitFirebaseRequest).enqueue(new Callback<AuthWithFirebaseResponse>() {
            @Override
            public void onResponse(Call<AuthWithFirebaseResponse> call, Response<AuthWithFirebaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    switch (response.body().getStatus()) {
                        case 0:
                            //იუზერი არის
                            iSignInFragment.onFireBaseAuthSignIn(response.body());
                            break;
                        case 1:
                            //იუზერი არ არი
                            iSignInFragment.onFireBaseAuthSignUp();
                            break;
                    }

                } else {
                    Log.e("respones", response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthWithFirebaseResponse> call, Throwable t) {
                Log.e("respones", t.getMessage());
            }
        });
    }

    private void getAppSettings() {
        apiService.getAppSettings().enqueue(new Callback<AppSettingsResponse>() {
            @Override
            public void onResponse(Call<AppSettingsResponse> call, Response<AppSettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        iSignInFragment.onGetSettings(response.body().getApp_settings());
                    } else {
                        iSignInFragment.onGetSettingsError(response.message());
                    }
                } else {
                    iSignInFragment.onGetSettingsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AppSettingsResponse> call, Throwable t) {
                iSignInFragment.onGetSettingsError(t.getMessage());
            }
        });
    }


    private String generateKey() {

        String keyToken = null;
        KeyGenerator gen;
        try {
            gen = KeyGenerator.getInstance("AES");
            gen.init(128); /* 32-bit AES */
            SecretKey secret = gen.generateKey();
            byte[] binary = secret.getEncoded();
            keyToken = String.format("%032X", new BigInteger(+1, binary));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyToken;
    }

}
