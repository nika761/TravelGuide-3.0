package com.example.travelguide.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.ui.login.activity.TermsAndPrivacyActivity;

public class HelperUI {

    public static final int BACKGROUND_WARNING = R.drawable.bg_fields_warning;
    public static final int BACKGROUND_DEF_WHITE = R.drawable.bg_sign_in_fields;
    public static final int BACKGROUND_DEF_BLACK = R.drawable.bg_signup_fields;

    public static final int RED = Color.red(R.color.red);
    public static final int BLACK = R.color.black;
    public static final int WHITE = R.color.white;

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

    public static boolean checkEmail(String enteredEmail) {
        boolean emailValidate = false;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = enteredEmail.trim();

        if (email.matches(emailPattern)) {
            emailValidate = true;
        }
        return emailValidate;
    }

    public static boolean checkPassword(String enteredPassword) {
        boolean passwordValidate = true;

        if (enteredPassword.trim().length() < 8) {
            passwordValidate = false;
        }
        return passwordValidate;
    }


    public static boolean checkConfirmPassword(String password, String confirmPassword) {
        boolean confirmPasswordValidate = false;

        if (password.equals(confirmPassword)) {
            confirmPasswordValidate = true;
        }
        return confirmPasswordValidate;
    }

    @SuppressLint("ResourceAsColor")
    public static void setBackgroundWarning(EditText currentField, TextView currentHead, String currentHeadText) {
        currentField.setBackgroundResource(BACKGROUND_WARNING);
        currentHead.setText(String.format("* %s", currentHeadText));
        currentHead.setTextColor(RED);
        YoYo.with(Techniques.Shake)
                .duration(300)
                .playOn(currentField);
    }

    public static void setBackgroundDefault(EditText currentField, TextView currentHead, String currentHeadText, int color, int bgColor) {
        currentField.setBackgroundResource(bgColor);
        currentHead.setText(currentHeadText);
        currentHead.setTextColor(color);
    }

    public static String checkEditTextData(EditText currentField, TextView currentHead, String currentHeadText, int colorDefault, int bgColor) {
        String currentStringData = null;
        if (currentField.getText().toString().isEmpty()) {
            setBackgroundWarning(currentField, currentHead, currentHeadText);
        } else {
            setBackgroundDefault(currentField, currentHead, currentHeadText, colorDefault, bgColor);
            currentStringData = currentField.getText().toString();
        }
        return currentStringData;
    }

}

