package travelguideapp.ge.travelguide.helper;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;
import travelguideapp.ge.travelguide.ui.home.comments.CommentFragment;
import travelguideapp.ge.travelguide.ui.webView.WebActivity;

import java.util.Calendar;

public class HelperUI {

    public static final int BACKGROUND_WARNING = R.drawable.bg_fields_warning;
    public static final int BACKGROUND_DEF_WHITE = R.drawable.bg_sign_in_fields;
    public static final int BACKGROUND_DEF_BLACK = R.drawable.bg_signup_fields;

    public static final int BLACK = R.color.black;
    public static final int WHITE = R.color.white;

    public static final String TYPE = "type";
    public static final String GO_URL = "URL";

    public static void startWebActivity(Context context, LoadWebViewBy requestFor, String url) {
        Intent termsIntent = new Intent(context, WebActivity.class);
        termsIntent.putExtra(GO_URL, url);
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


    public static void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(target.getContext(), animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    public static void inputWarning(Activity activity, View view, TextView textView) {
        view.setBackground(activity.getResources().getDrawable(R.drawable.bg_fields_warning, null));
        if (view instanceof EditText)
            ((EditText) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_edit_red, 0);
        textView.setTextColor(activity.getResources().getColor(R.color.red, null));
        YoYo.with(Techniques.Shake).duration(300).playOn(view);
    }

    public static void inputDefault(Activity activity, View view, TextView textView) {
        view.setBackground(activity.getResources().getDrawable(R.drawable.selector_input_field, null));
        if (view instanceof EditText)
            ((EditText) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_input_field_icon, 0);
        textView.setTextColor(activity.getResources().getColor(R.color.black, null));
    }
}

