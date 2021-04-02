package travelguideapp.ge.travelguide.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.callback.AskingDialogCallback;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;

public class DialogManager {

    public static void signUpConfirmDialog(Activity activity, String title, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View dialogLayout = activity.getLayoutInflater().inflate(R.layout.c_registration_confirm, null);
            TextView verifyTitle, verifyMessage;
            verifyTitle = dialogLayout.findViewById(R.id.confirm_title);
            verifyMessage = dialogLayout.findViewById(R.id.confirm_message);
            verifyTitle.setText(title);
            verifyMessage.setText(message);
            builder.setView(dialogLayout);
            builder.setCancelable(false);

            AlertDialog signUpDialog = builder.create();
            if (signUpDialog.getWindow() != null) {
                signUpDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent));
                signUpDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
            }

            signUpDialog.setOnShowListener(dialog -> new Handler().postDelayed(() -> {
                try {
                    dialog.dismiss();
                    activity.startActivity(SignInActivity.getRedirectIntent(activity));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 3000));

            signUpDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static AlertDialog profileInfoUpdatedDialog(Activity activity, String title, String body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View dialogLayout = activity.getLayoutInflater().inflate(R.layout.dialog_profile_updated, null);

        TextView titleTxt = dialogLayout.findViewById(R.id.dialog_profile_updated_title);
        titleTxt.setText(title);

        TextView bodyTxt = dialogLayout.findViewById(R.id.dialog_profile_updated_body);
        bodyTxt.setText(body);

        builder.setView(dialogLayout);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.setCancelable(false);
        return dialog;
    }

    public static void datePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, long timeMillis) {
        Calendar cal = Calendar.getInstance();
        int year;
        int month;
        int day;

        if (timeMillis == 0) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            cal.setTimeInMillis(timeMillis);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.show();

    }

    public static void getAskingDialog(Context context, String question, AskingDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(question)
                .setPositiveButton(context.getString(R.string.yes), (dialog, which) -> callback.onYes())
                .setNegativeButton(context.getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .create();

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_sign_out_dialog));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.show();
    }

}
