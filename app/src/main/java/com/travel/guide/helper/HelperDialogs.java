package com.travel.guide.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.travel.guide.R;

public class HelperDialogs {

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

        dialog.show();
    }


}
