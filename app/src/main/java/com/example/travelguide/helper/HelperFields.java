package com.example.travelguide.helper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;

public class HelperFields {

    public static final int BACKGROUND_WARNING = R.drawable.bg_fields_warning;
    public static final int BACKGROUND_DEF_WHITE = R.drawable.bg_sign_in_fields;
    public static final int BACKGROUND_DEF_BLACK = R.drawable.bg_signup_fields;

    public static final int RED = Color.red(R.color.red);
    public static final int BLACK = R.color.black;
    public static final int WHITE = R.color.white;

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


    public static boolean confirmPassword(String password, String confirmPassword) {
        boolean confirmPasswordValidate = false;

        if (password.equals(confirmPassword)) {
            confirmPasswordValidate = true;
        }
        return confirmPasswordValidate;
    }

    @SuppressLint("ResourceAsColor")
    public static void setFieldsWarning(EditText currentField, TextView currentHead, String currentHeadText) {
        currentField.setBackgroundResource(BACKGROUND_WARNING);
        currentHead.setText(String.format("* %s", currentHeadText));
        currentHead.setTextColor(RED);
        YoYo.with(Techniques.Shake)
                .duration(300)
                .playOn(currentField);
    }

    public static void setFieldsDefault(EditText currentField, TextView currentHead, String currentHeadText, int color, int bgColor) {
        currentField.setBackgroundResource(bgColor);
        currentHead.setText(currentHeadText);
        currentHead.setTextColor(color);
    }

    public static String checkEditTextData(EditText currentField, TextView currentHead, String currentHeadText, int colorDefault, int bgColor) {
        String currentStringData = null;
        if (currentField.getText().toString().isEmpty()) {
            setFieldsWarning(currentField, currentHead, currentHeadText);
        } else {
            setFieldsDefault(currentField, currentHead, currentHeadText, colorDefault, bgColor);
            currentStringData = currentField.getText().toString();
        }
        return currentStringData;
    }

}
