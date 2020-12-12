package com.travel.guide.ui.login.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.travel.guide.R;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.model.request.ChangePasswordRequest;

import java.util.HashMap;


public class ChangePasswordFragment extends Fragment {

    public static ChangePasswordFragment getInstance(ChangePasswordListener callback) {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        changePasswordFragment.callback = callback;
        return changePasswordFragment;
    }

    private ChangePasswordListener callback;

    private TextView currentHead, passwordHead, confirmHead;
    private EditText currentField, passwordField, confirmField;

    private Button save;

    private String currentPassword, password, passwordConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        currentHead = view.findViewById(R.id.change_password_current_head);
        passwordHead = view.findViewById(R.id.change_password_head);
        confirmHead = view.findViewById(R.id.change_password_confirm_head);

        currentField = view.findViewById(R.id.change_password_current_field);
        passwordField = view.findViewById(R.id.change_password_field);
        confirmField = view.findViewById(R.id.change_password_confirm_field);


        save = view.findViewById(R.id.change_password_save);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickAction();
    }


    private void clickAction() {
        save.setOnClickListener(v -> {

            currentPassword = HelperUI.checkEditTextData(currentField, currentHead, "Current Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());
            if (currentPassword != null && HelperUI.checkPassword(currentPassword))
                HelperUI.setBackgroundDefault(currentField, currentHead, "Current Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(currentField, currentHead, "Current Password", currentField.getContext());


            password = HelperUI.checkEditTextData(passwordField, passwordHead, "Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());
            if (password != null && HelperUI.checkPassword(password))
                HelperUI.setBackgroundDefault(passwordField, passwordHead, "Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(passwordField, passwordHead, "Password", currentField.getContext());


            passwordConfirm = HelperUI.checkEditTextData(confirmField, confirmHead, "Confirm Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());
            if (passwordConfirm != null && HelperUI.checkConfirmPassword(password, passwordConfirm))
                HelperUI.setBackgroundDefault(confirmField, confirmHead, "Confirm Password", HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(confirmField, confirmHead, "Confirm Password", currentField.getContext());


            if (currentPassword != null && password != null && passwordConfirm != null)
                if (HelperUI.checkPassword(currentPassword) && HelperUI.checkPassword(password) && HelperUI.checkConfirmPassword(password, passwordConfirm)) {
                    callback.onPasswordChoose(new ChangePasswordRequest(currentPassword, password, passwordConfirm));
                } else
                    Toast.makeText(currentField.getContext(), "Password not match", Toast.LENGTH_SHORT).show();
        });
    }



    interface ChangePasswordListener {
        void onPasswordChoose(ChangePasswordRequest passwordModel);
    }

}
