package com.travel.guide.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.travel.guide.R;
import com.travel.guide.enums.InputFieldPairs;
import com.travel.guide.enums.LoadWebViewBy;
import com.travel.guide.ui.home.comments.CommentFragment;
import com.travel.guide.ui.webView.WebActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HelperUI {

    public static final int BACKGROUND_WARNING = R.drawable.bg_fields_warning;
    public static final int BACKGROUND_DEF_WHITE = R.drawable.bg_sign_in_fields;
    public static final int BACKGROUND_DEF_BLACK = R.drawable.bg_signup_fields;

    public static final int BLACK = R.color.black;
    public static final int WHITE = R.color.white;

    public static final String TYPE = "type";

    public static void startWebActivity(Context context, LoadWebViewBy requestFor) {
        Intent termsIntent = new Intent(context, WebActivity.class);
        termsIntent.putExtra(TYPE, requestFor);
        context.startActivity(termsIntent);
    }

    public static void loadFragment(Fragment currentFragment, Bundle data, int fragmentID,
                                    boolean backStack, boolean replace, FragmentActivity fragmentActivity) {

        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        currentFragment.setArguments(data);

        if (currentFragment instanceof CommentFragment)
            fragmentTransaction.setCustomAnimations(R.anim.anim_fragment_slide_up, R.anim.anim_swipe_bottom);

        if (backStack)
            fragmentTransaction.addToBackStack(null);

        if (replace)
            fragmentTransaction.replace(fragmentID, currentFragment);
        else
            fragmentTransaction.add(fragmentID, currentFragment);


        fragmentTransaction.commit();
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

    public static void setBackgroundWarning(EditText currentField, TextView currentHead, String currentHeadText, Context context) {

        currentField.setBackgroundResource(BACKGROUND_WARNING);
        currentHead.setText(String.format("* %s", currentHeadText));
        currentHead.setTextColor(context.getResources().getColor(R.color.red, null));
        YoYo.with(Techniques.Shake)
                .duration(300)
                .playOn(currentField);
    }

    public static void setBackgroundDefault(EditText currentField, TextView currentHead, String currentHeadText, int color, int bgColor) {
        currentField.setBackgroundResource(bgColor);
        currentHead.setText(currentHeadText);
        currentHead.setTextColor(color);
    }

    public static String checkEditTextData(EditText currentField, TextView currentHead, String currentHeadText, int colorDefault, int bgColor, Context context) {
        String currentStringData = null;
        if (currentField.getText().toString().isEmpty()) {
            setBackgroundWarning(currentField, currentHead, currentHeadText, context);
        } else {
            setBackgroundDefault(currentField, currentHead, currentHeadText, colorDefault, bgColor);
            currentStringData = currentField.getText().toString();
        }
        return currentStringData;
    }


    public static int calculateNoOfColumns(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static String getDateFromMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return DateFormat.format("dd-MM-yyyy", calendar).toString();
    }

    public static void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(target.getContext(), animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private static void inputWarning(Activity activity, EditText editText, TextView textView) {
        editText.setBackground(activity.getResources().getDrawable(R.drawable.bg_fields_warning, null));
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_edit_red, 0);
        textView.setTextColor(activity.getResources().getColor(R.color.red, null));
        YoYo.with(Techniques.Shake).duration(300).playOn(editText);
    }

    private static void inputDefault(Activity activity, EditText editText, TextView textView) {
        editText.setBackground(activity.getResources().getDrawable(R.drawable.selector_input_field, null));
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_input_field_icon, 0);
        textView.setTextColor(activity.getResources().getColor(R.color.black, null));
    }

    public static HashMap<InputFieldPairs, String> checkInputData(Activity activity, HashMap<InputFieldPairs,
            HashMap<TextView, EditText>> inputFields) {

        HashMap<InputFieldPairs, String> vars = new HashMap<>();

        for (Map.Entry<InputFieldPairs, HashMap<TextView, EditText>> currentEntry : inputFields.entrySet()) {
            for (Map.Entry<TextView, EditText> currentViewEntry : currentEntry.getValue().entrySet()) {
                switch (currentEntry.getKey()) {
                    default:
                        if (currentViewEntry.getValue().getText().toString().isEmpty()) {
                            inputWarning(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        } else {
                            vars.put(currentEntry.getKey(), currentViewEntry.getValue().getText().toString());
                            inputDefault(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        }
                        break;

                    case EMAIL:
                        if (HelperUI.checkEmail(currentViewEntry.getValue().getText().toString())) {
                            vars.put(InputFieldPairs.EMAIL, currentViewEntry.getValue().getText().toString());
                            inputDefault(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        } else {
                            inputWarning(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        }
                        break;

                    case PASSWORD:
                        if (HelperUI.checkPassword(currentViewEntry.getValue().getText().toString())) {
                            vars.put(InputFieldPairs.PASSWORD, currentViewEntry.getValue().getText().toString());
                            inputDefault(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        } else {
                            inputWarning(activity, currentViewEntry.getValue(), currentViewEntry.getKey());
                        }
                        break;

                    case COUNTRY:

                    case BIO:
                        if (!currentViewEntry.getValue().getText().toString().isEmpty())
                            vars.put(currentEntry.getKey(), currentViewEntry.getValue().getText().toString());

                        break;

                }
            }
        }
        return vars;
    }

}

