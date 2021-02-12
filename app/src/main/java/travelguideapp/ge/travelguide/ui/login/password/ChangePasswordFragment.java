package travelguideapp.ge.travelguide.ui.login.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.language.GlobalLanguages;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class ChangePasswordFragment extends Fragment {

    public static ChangePasswordFragment getInstance(ChangePasswordListener callback) {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        changePasswordFragment.callback = callback;
        return changePasswordFragment;
    }

    private ChangePasswordListener callback;

    private TextView mainHead, currentHead, passwordHead, confirmHead;
    private EditText currentField, passwordField, confirmField;

    private String textMainHead, textSaveHead, textPasswordHead, textConfirmHead, textPasswordNotMatch, textCurrentPassword;

    private Button save;

    private String currentPassword, password, passwordConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        mainHead = view.findViewById(R.id.change_password_main_head);
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

    private void getStringsByLanguage() {
        GlobalLanguages currentLanguage = GlobalPreferences.getCurrentLanguage(save.getContext());

        try {
            if (currentLanguage.getReset_password_intro() != null) {
                textMainHead = currentLanguage.getReset_password_intro();
                mainHead.setText(textMainHead);
            }
        } catch (Exception e) {
            textMainHead = getString(R.string.reset_password_intro);
            mainHead.setText(textMainHead);
        }

        try {
            if (currentLanguage.getPassword_field_head() != null) {
                textPasswordHead = currentLanguage.getPassword_field_head();
                passwordHead.setText(textPasswordHead);
            }
        } catch (Exception e) {
            textPasswordHead = getString(R.string.password);
            passwordHead.setText(textPasswordHead);
        }

//        try {
//            if (currentLanguage.getCurrentPassword() != null) {
//                textCurrentPassword = currentLanguage.getCurrentPassword();
//                currentHead.setText(textCurrentPassword);
//            }
//        } catch (Exception e) {
//            textCurrentPassword = getString(R.string.current_password);
//            currentHead.setText(textCurrentPassword);
//        }

        try {
            if (currentLanguage.getConfirm_password_filed_head() != null) {
                textConfirmHead = currentLanguage.getConfirm_password_filed_head();
                confirmHead.setText(textConfirmHead);
            }
        } catch (Exception e) {
            textConfirmHead = getString(R.string.confirm_password);
            confirmHead.setText(textConfirmHead);
        }

        try {
            if (currentLanguage.getSave() != null) {
                textSaveHead = currentLanguage.getSave();
                save.setText(textSaveHead);
            }
        } catch (Exception e) {
            textSaveHead = getString(R.string.save);
            save.setText(textSaveHead);
        }

        try {
            if (currentLanguage.getPassword_not_match() != null) {
                textPasswordNotMatch = currentLanguage.getPassword_not_match();
            }
        } catch (Exception e) {
            textPasswordNotMatch = getString(R.string.not_match_password);
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
            if (passwordConfirm != null && HelperUI.checkConfirmPassword(password, passwordConfirm))
                HelperUI.setBackgroundDefault(confirmField, confirmHead, getString(R.string.confirm_password), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
            else
                HelperUI.setBackgroundWarning(confirmField, confirmHead, getString(R.string.confirm_password), currentField.getContext());

            if (currentPassword != null && password != null && passwordConfirm != null)
                if (HelperUI.checkPassword(currentPassword) && HelperUI.checkPassword(password) && HelperUI.checkConfirmPassword(password, passwordConfirm)) {
                    save.setClickable(false);
                    callback.onPasswordChoose(new ChangePasswordRequest(currentPassword, password, passwordConfirm));
                } else {
                    save.setClickable(true);
                    MyToaster.getErrorToaster(currentField.getContext(), textPasswordNotMatch);
                }
        });
    }


    interface ChangePasswordListener {
        void onPasswordChoose(ChangePasswordRequest passwordModel);
    }

}
