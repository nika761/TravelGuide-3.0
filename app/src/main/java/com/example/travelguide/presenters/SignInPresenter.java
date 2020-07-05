package com.example.travelguide.presenters;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.travelguide.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.model.request.LoginRequestModel;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.travelguide.utils.UtilsPref.FACEBOOK;
import static com.example.travelguide.utils.UtilsPref.GOOGLE;

public class SignInPresenter {
    private ISignInFragment iSignInFragment;
    private ApiService apiService;

    public SignInPresenter(ISignInFragment iSignInFragment) {
        this.iSignInFragment = iSignInFragment;
        apiService = RetrofitManager.getApiService();
    }


    public void fetchFbUserData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                if (object != null) {
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String id = object.getString("id");
                    String email = object.getString("email");
                    String url = "https://graph.facebook.com/" + id + "/picture?type=large";
                    User user = new User(firstName, lastName, url, id, email, FACEBOOK);
                    iSignInFragment.onGetFbUserData(user);
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

    public void fetchGglUserData(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                String personPhotoUrl;
                Uri personPhotoUri = account.getPhotoUrl();
                if (personPhotoUri != null) {
                    personPhotoUrl = personPhotoUri.toString();
                } else {
                    personPhotoUrl = null;
                }
                User user = new User(personGivenName, personFamilyName, personPhotoUrl, personId, personEmail, GOOGLE);
                iSignInFragment.onGetGglUserData(user);

            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void sentLoginRequest(LoginRequestModel loginRequestModel) {
        apiService.signIn(loginRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    iSignInFragment.onGetLoginResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

            }
        });
    }
}
