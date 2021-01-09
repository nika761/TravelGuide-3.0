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

        try {
            GlobalLanguages currentLanguage = GlobalPreferences.getCurrentLanguage(save.getContext());

            mainHead.setText(currentLanguage.getReset_password_intro());
//            currentHead.setText(currentLanguage.head());
            passwordHead.setText(currentLanguage.getPassword_field_head());
            confirmHead.setText(currentLanguage.getConfirm_password_filed_head());
            save.setText(currentLanguage.getSave());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickAction();
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
                    callback.onPasswordChoose(new ChangePasswordRequest(currentPassword, password, passwordConfirm));
                } else
                    MyToaster.getErrorToaster(currentField.getContext(), getString(R.string.not_match_password));
        });
    }


    interface ChangePasswordListener {
        void onPasswordChoose(ChangePasswordRequest passwordModel);
    }

}
