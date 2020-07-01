package com.example.travelguide.utils;

import android.content.Context;
import android.content.Intent;

import com.example.travelguide.activity.TermsAndPrivacyActivity;

public class UtilsTerms {

    public static final String TERMS = "terms";
    public static final String POLICY = "policy";
    public static final String TYPE = "type";

    public static void startTermsAndPolicyActivity(Context context, String requestFor) {
        Intent termsIntent = new Intent(context, TermsAndPrivacyActivity.class);
        termsIntent.putExtra(TYPE, requestFor);
        context.startActivity(termsIntent);
    }
}
