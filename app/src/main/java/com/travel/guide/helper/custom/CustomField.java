package com.travel.guide.helper.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.travel.guide.R;

public class CustomField extends LinearLayout {

    private EditText field;
    private TextView head;

    private boolean isErrorShowing;

    public CustomField(@NonNull Context context) {
        super(context);
    }

    public CustomField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomField(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 38);
        lp.leftMargin = 3;
        lp.rightMargin = 3;

        EditText editText = new EditText(context);
        editText.setLayoutParams(lp);
        addView(editText);
    }

    public void setViews() {

    }

    public void showError() {
        isErrorShowing = true;
        field.setBackgroundResource(R.drawable.bg_fields_warning);
    }

    public void hideError() {
        if (isErrorShowing) {
            field.setBackgroundResource(R.drawable.bg_signup_fields);
        }
    }

    private boolean checkStringForNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public String getInputText() {
        if (!checkStringForNullOrEmpty(field.getText().toString())) {
            hideError();
            return field.getText().toString();
        } else {
            showError();
            return null;
        }
    }

}
