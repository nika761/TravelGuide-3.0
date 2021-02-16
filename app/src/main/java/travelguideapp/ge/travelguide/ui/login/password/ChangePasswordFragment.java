package travelguideapp.ge.travelguide.ui.login.password;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;

public class ChangePasswordFragment extends Fragment {

    private enum PasswordType {
        CURRENT, NEW_PASSWORD, CONFIRM_PASSWORD
    }

    public static ChangePasswordFragment getInstance(ChangePasswordListener callback) {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        changePasswordFragment.callback = callback;
        return changePasswordFragment;
    }

    private ChangePasswordListener callback;

    private ImageButton currentPasswordBtn, newPasswordBtn, confirmPasswordBtn;
    private TextView currentHead, passwordHead, confirmHead;
    private EditText currentField, passwordField, confirmField;
    private Drawable hided, showed;
    private Button save;

    private boolean currentPasswordHidden = true, newPasswordHidden = true, confirmPasswordHidden = true;
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

        currentPasswordBtn = view.findViewById(R.id.change_password_current_visible_btn);
        currentPasswordBtn.setOnClickListener(v -> onPasswordStateChange(PasswordType.CURRENT));

        newPasswordBtn = view.findViewById(R.id.change_password_visible_btn);
        newPasswordBtn.setOnClickListener(v -> onPasswordStateChange(PasswordType.NEW_PASSWORD));

        confirmPasswordBtn = view.findViewById(R.id.change_password_confirm_visible_btn);
        confirmPasswordBtn.setOnClickListener(v -> onPasswordStateChange(PasswordType.CONFIRM_PASSWORD));

        save = view.findViewById(R.id.change_password_save);

        hided = ContextCompat.getDrawable(save.getContext(), R.drawable.ic_password_hide_yellow);

        showed = ContextCompat.getDrawable(save.getContext(), R.drawable.ic_password_show_yellow);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickAction();
    }

    private void onPasswordStateChange(PasswordType passwordHidden) {
        try {
            switch (passwordHidden) {
                case CURRENT:
                    if (currentPasswordHidden) {
                        setField(currentField, true);
                        currentPasswordBtn.setBackground(hided);
                        currentPasswordHidden = false;
                    } else {
                        setField(currentField, false);
                        currentPasswordBtn.setBackground(showed);
                        currentPasswordHidden = true;
                    }
                    break;

                case NEW_PASSWORD:
                    if (newPasswordHidden) {
                        setField(passwordField, true);
                        newPasswordBtn.setBackground(hided);
                        newPasswordHidden = false;
                    } else {
                        setField(passwordField, false);
                        newPasswordBtn.setBackground(showed);
                        newPasswordHidden = true;
                    }
                    break;

                case CONFIRM_PASSWORD:
                    if (confirmPasswordHidden) {
                        setField(confirmField, true);
                        confirmPasswordBtn.setBackground(hided);
                        confirmPasswordHidden = false;
                    } else {
                        setField(confirmField, false);
                        confirmPasswordBtn.setBackground(showed);
                        confirmPasswordHidden = true;
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setField(EditText field, boolean show) {
        try {
            if (show) {
                field.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            field.setSelection(field.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSaveBtn(boolean clickable) {
        try {
            save.setClickable(clickable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickAction() {
        save.setOnClickListener(v -> {
            currentPassword = HelperUI.checkEditTextData(currentField, currentHead, getString(R.string.current_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());

            if (currentPassword != null && HelperUI.checkPassword(currentPassword))
                HelperUI.setBackgroundDefault(currentField, currentHead, getString(R.string.current_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(currentField, currentHead, getString(R.string.current_password), currentField.getContext());


            password = HelperUI.checkEditTextData(passwordField, passwordHead, getString(R.string.password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());
            if (password != null && HelperUI.checkPassword(password))
                HelperUI.setBackgroundDefault(passwordField, passwordHead, getString(R.string.password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(passwordField, passwordHead, getString(R.string.password), currentField.getContext());


            passwordConfirm = HelperUI.checkEditTextData(confirmField, confirmHead, getString(R.string.confirm_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK, save.getContext());
            if (passwordConfirm != null && password != null && HelperUI.checkConfirmPassword(password, passwordConfirm))
                HelperUI.setBackgroundDefault(confirmField, confirmHead, getString(R.string.confirm_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(confirmField, confirmHead, getString(R.string.confirm_password), currentField.getContext());

            if (currentPassword != null && password != null && passwordConfirm != null) {
                if (HelperUI.checkPassword(currentPassword) && HelperUI.checkPassword(password) && HelperUI.checkConfirmPassword(password, passwordConfirm)) {
                    save.setClickable(false);
                    callback.onPasswordChoose(new ChangePasswordRequest(currentPassword, password, passwordConfirm));
                } else {
                    save.setClickable(true);
                    MyToaster.getToast(save.getContext(), getString(R.string.not_match_password));
                }
            }
        });
    }


    interface ChangePasswordListener {
        void onPasswordChoose(ChangePasswordRequest passwordModel);
    }

}
