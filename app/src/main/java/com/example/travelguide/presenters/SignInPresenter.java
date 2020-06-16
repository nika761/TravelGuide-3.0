package com.example.travelguide.presenters;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.travelguide.interfaces.ISignInFragment;
import com.example.travelguide.model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

public class SignInPresenter {
    private ISignInFragment iSignInFragment;

    public SignInPresenter(ISignInFragment iSignInFragment) {
        this.iSignInFragment = iSignInFragment;
    }


    public void fetchFbUserData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                if (object != null) {
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String id = object.getString("id");
                    String email = object.getString("email");
                    String loginType = "facebook";
                    String url = "https://graph.facebook.com/" + id + "/picture?type=large";
                    User user = new User(firstName, lastName, url, id, email, loginType);
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
                String loginType = "google";
                String personPhotoUrl;
                Uri personPhotoUri = account.getPhotoUrl();
                if (personPhotoUri != null) {
                    personPhotoUrl = personPhotoUri.toString();
                } else {
                    personPhotoUrl = null;
                }
                User user = new User(personGivenName, personFamilyName, personPhotoUrl, personId, personEmail, loginType);
                iSignInFragment.onGetGglUserData(user);

            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
