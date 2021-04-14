package travelguideapp.ge.travelguide.ui.login.signIn;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import travelguideapp.ge.travelguide.model.customModel.FireBaseUserModel;
import travelguideapp.ge.travelguide.model.request.AuthWitFirebaseRequest;
import travelguideapp.ge.travelguide.model.request.LoginRequest;
import travelguideapp.ge.travelguide.model.request.VerifyEmailRequest;
import travelguideapp.ge.travelguide.model.response.AuthWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.LoginResponse;
import travelguideapp.ge.travelguide.model.response.VerifyEmailResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SignInPresenter {

    private final SignInListener signInListener;
    private final ApiService apiService;
    private final DatabaseReference database;
    private final FirebaseAuth firebaseAuth;
    private String key;

    SignInPresenter(SignInListener onSignListener) {
        this.signInListener = onSignListener;
        this.apiService = RetrofitManager.getApiService();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance().getReference("users");
    }

    void verify(VerifyEmailRequest verifyEmailRequest) {
        apiService.verifyEmail(verifyEmailRequest).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(@NotNull Call<VerifyEmailResponse> call, @NotNull Response<VerifyEmailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            signInListener.onVerify(response.body());
                            break;

                        case -100:
                            signInListener.onError(response.body().getStatus() + " not valid");
                            break;

                        default:
                            signInListener.onError(String.valueOf(response.body().getStatus()));
                    }
                } else {
                    signInListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VerifyEmailResponse> call, @NotNull Throwable t) {
                signInListener.onError(t.getMessage());
            }
        });
    }

    void authWithFb(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getUid();

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

                            new Handler(Looper.getMainLooper()).postDelayed(() -> authWithFireBase(new AuthWitFirebaseRequest(key)), 3000);

                            signInListener.onFireBaseSignUp(key, 1, firstName);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id");
                request.setParameters(parameters);
                request.executeAsync();
            } else {
                Log.e("facebookError", task.getException().getMessage());
            }
        });
    }

    void authWithGoogle(Task<GoogleSignInAccount> task) {
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

                        signInListener.onFireBaseSignUp(key, 2, personGivenName);

                        authWithFireBase(new AuthWitFirebaseRequest(key));

                    } else {
                        signInListener.onError(authResultTask.getException().getMessage());
                    }
                });
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            signInListener.onError(e.getLocalizedMessage());
        }
    }

    void singIn(LoginRequest loginRequest) {
        apiService.signIn(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            signInListener.onSign(response.body());
                            break;

                        case 1:
                        case -100:
                        case -101:
                            signInListener.onError(response.body().getMessage());
                            break;
                    }
                } else {
                    signInListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                signInListener.onError(t.getMessage());
            }
        });
    }

    private void authWithFireBase(AuthWitFirebaseRequest authWitFirebaseRequest) {
        apiService.authWithFirebase(authWitFirebaseRequest).enqueue(new Callback<AuthWithFirebaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthWithFirebaseResponse> call, @NotNull Response<AuthWithFirebaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            //იუზერი არის
                            signInListener.onFireBaseAuthSignIn(response.body());
                            break;

                        case 1:
                            //იუზერი არ არი
                            signInListener.onFireBaseAuthSignUp();
                            break;
                    }

                } else {
                    signInListener.onError("Error");
                }
            }

            @Override
            public void onFailure(@NotNull Call<AuthWithFirebaseResponse> call, @NotNull Throwable t) {
                signInListener.onError(t.getMessage());
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
