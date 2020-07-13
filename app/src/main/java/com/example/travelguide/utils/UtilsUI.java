package com.example.travelguide.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.activity.TermsAndPrivacyActivity;

public class UtilsUI {

    public static final String TERMS = "terms";
    public static final String POLICY = "policy";
    public static final String ABOUT = "about";
    public static final String TYPE = "type";

    public static void startTermsAndPolicyActivity(Context context, String requestFor) {
        Intent termsIntent = new Intent(context, TermsAndPrivacyActivity.class);
        termsIntent.putExtra(TYPE, requestFor);
        context.startActivity(termsIntent);
    }

    public static void loadFragment(Fragment currentFragment, Bundle data, int fragmentID,
                                    boolean backStack, FragmentActivity fragmentActivity) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentActivity
                .getSupportFragmentManager()
                .beginTransaction();

        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction
                .replace(fragmentID, currentFragment)
                .commit();
    }
}

