package com.example.travelguide.utils;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

public class UtilsGoogle {

    public static GoogleSignInClient initGoogleSignInClient(Context context) {

        GoogleSignInClient mGoogleSignInClient;

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        return mGoogleSignInClient;
    }
}
