package com.example.travelguide.utils;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;

public class UtilsFields {

    private static final int BACKGROUND_WARNING = R.drawable.background_signup_edittext_worning;
    private static final int BACKGROUND_DEFAULT = R.drawable.background_sign_in_edittexts;

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

    private static void setFieldsWarning(EditText currentField, TextView currentHead, String currentHeadText,int color) {
        currentField.setBackgroundResource(BACKGROUND_WARNING);
        currentHead.setText(String.format("* %s", currentHeadText));
        currentHead.setTextColor(color);
        YoYo.with(Techniques.Shake)
                .duration(300)
                .playOn(currentField);
    }

    @SuppressLint("ResourceAsColor")
    private static void setFieldsDefault(EditText currentField, TextView currentHead, String currentHeadText,int color) {
        currentField.setBackgroundResource(BACKGROUND_DEFAULT);
        currentHead.setText(currentHeadText);
        currentHead.setTextColor(color);
    }

    public static String checkEditTextData(EditText currentField, TextView currentHead, String currentHeadText, String currentStringData,int colorRed,int colorBlack) {

        if (currentField.getText().toString().isEmpty()) {
            setFieldsWarning(currentField, currentHead, currentHeadText,colorRed);
        } else {
            setFieldsDefault(currentField, currentHead, currentHeadText,colorBlack);
            currentStringData = currentField.getText().toString();
        }
        return currentStringData;
    }

}
