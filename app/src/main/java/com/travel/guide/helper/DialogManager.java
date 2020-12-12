package com.travel.guide.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.travel.guide.R;

import java.util.Calendar;

public class DialogManager {

    public static void signUpConfirmDialog(Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View customLayout = activity.getLayoutInflater().inflate(R.layout.c_registration_confirm, null);

        TextView verifyTitle, verifyMessage;

        verifyTitle = customLayout.findViewById(R.id.confirm_title);
        verifyMessage = customLayout.findViewById(R.id.confirm_message);

        verifyTitle.setText(title);
        verifyMessage.setText(message);

        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg_transparent, null));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }
        dialog.show();
    }


    public static void profileInfoUpdatedDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View customLayout = activity.getLayoutInflater().inflate(R.layout.dialog_profile_updated, null);

        TextView verifyTitle, verifyMessage;

        verifyTitle = customLayout.findViewById(R.id.confirm_title);
        verifyMessage = customLayout.findViewById(R.id.confirm_message);

//        verifyTitle.setText(title);
//        verifyMessage.setText(message);

        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg_transparent, null));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.setCancelable(false);
        dialog.show();
    }

    public static void datePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.show();
    }


}
