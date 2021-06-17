package travelguideapp.ge.travelguide.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.listener.QuestionDialogListener;
import travelguideapp.ge.travelguide.listener.DialogDismissListener;
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

    public static AlertDialog profileUpdatedDialog(Activity activity, String title, String body) {
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

    public static void questionDialog(Context context, String question, QuestionDialogListener callback) {
        try {
            if (context == null) {
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.questionDialogTextColor);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void defaultDialog(Activity activity, String message, DialogDismissListener dialogDismissListener) {
        try {
            if (activity == null) {
                return;
            }

            Dialog defaultDialog = new Dialog(activity);
            View defaultDialogView = activity.getLayoutInflater().inflate(R.layout.dialog_default, null);

            TextView bodyTxt = defaultDialogView.findViewById(R.id.dialog_error_body);

            if (message == null) {
                bodyTxt.setText(activity.getString(R.string.default_error_message));
            } else {
                bodyTxt.setText(message);
            }

            Button closeBtn = defaultDialogView.findViewById(R.id.dialog_error_btn);

            defaultDialog.setContentView(defaultDialogView);
            defaultDialog.setCancelable(false);

            if (dialogDismissListener != null)
                defaultDialog.setOnDismissListener(dialog -> dialogDismissListener.onClosed());

            closeBtn.setOnClickListener(v -> defaultDialog.dismiss());

            if (defaultDialog.getWindow() != null) {
                defaultDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                defaultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                errorDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
            }

            defaultDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AlertDialog noInternetConnectionDialog(Activity activity) {
        try {

            if (activity == null) {
                return null;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View errorDialogLayout = activity.getLayoutInflater().inflate(R.layout.dialog_no_internet_connection, null);

            Button closeBtn = errorDialogLayout.findViewById(R.id.no_internet_connection_btn);

            builder.setView(errorDialogLayout);

            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);

            closeBtn.setOnClickListener(v -> alertDialog.dismiss());

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent));
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
            }

            return alertDialog;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Dialog progressDialog(Activity activity) {
        try {

            if (activity == null) {
                return null;
            }

            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_progress);
            try {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.setCancelable(false);
            dialog.create();
            return dialog;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
