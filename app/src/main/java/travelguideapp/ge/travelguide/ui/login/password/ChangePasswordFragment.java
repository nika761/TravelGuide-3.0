package travelguideapp.ge.travelguide.ui.login.password;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseFragment;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;

import static travelguideapp.ge.travelguide.utility.LogUtils.LOG_E;
import static travelguideapp.ge.travelguide.utility.LogUtils.makeLogTag;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordListener {

    private static final String TAG = makeLogTag(ChangePasswordFragment.class);

    private static final String CURRENT_PASSWORD = "current_password";
    private static final String CONFIRM_PASSWORD = "confirm_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String SHOW_BTN = "show_button";
    private static final String HIDE_BTN = "hide_button";

    public static ChangePasswordFragment getInstance() {
        return new ChangePasswordFragment();
    }

    private ChangePasswordPresenter presenter;

    private ImageButton newPasswordBtn, currentPasswordBtn, confirmPasswordBtn;
    private TextView currentHead, passwordHead, confirmHead;
    private EditText currentField, passwordField, confirmField;
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
        currentPasswordBtn.setOnClickListener(v -> onPasswordStateChange(CURRENT_PASSWORD));

        newPasswordBtn = view.findViewById(R.id.change_password_visible_btn);
        newPasswordBtn.setOnClickListener(v -> onPasswordStateChange(NEW_PASSWORD));

        confirmPasswordBtn = view.findViewById(R.id.change_password_confirm_visible_btn);
        confirmPasswordBtn.setOnClickListener(v -> onPasswordStateChange(CONFIRM_PASSWORD));

        save = view.findViewById(R.id.change_password_save);
        save.setOnClickListener(v -> checkPasswords());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        attachPresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachPresenter();
    }

    private void onPasswordStateChange(String passwordType) {
        try {
            switch (passwordType) {
                case CURRENT_PASSWORD:
                    if (currentPasswordHidden) {
                        setPasswordState(currentField, true);
                        setBtnByPasswordState(currentPasswordBtn, HIDE_BTN);
                        currentPasswordHidden = false;
                    } else {
                        setPasswordState(currentField, false);
                        setBtnByPasswordState(currentPasswordBtn, SHOW_BTN);
                        currentPasswordHidden = true;
                    }
                    break;

                case NEW_PASSWORD:
                    if (newPasswordHidden) {
                        setPasswordState(passwordField, true);
                        setBtnByPasswordState(newPasswordBtn, HIDE_BTN);
                        newPasswordHidden = false;
                    } else {
                        setPasswordState(passwordField, false);
                        setBtnByPasswordState(newPasswordBtn, SHOW_BTN);
                        newPasswordHidden = true;
                    }
                    break;

                case CONFIRM_PASSWORD:
                    if (confirmPasswordHidden) {
                        setPasswordState(confirmField, true);
                        setBtnByPasswordState(confirmPasswordBtn, HIDE_BTN);
                        confirmPasswordHidden = false;
                    } else {
                        setPasswordState(confirmField, false);
                        setBtnByPasswordState(confirmPasswordBtn, SHOW_BTN);
                        confirmPasswordHidden = true;
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setBtnByPasswordState(ImageButton imageButton, String buttonType) {
        try {
            switch (buttonType) {
                case SHOW_BTN:
                    imageButton.setBackground(ContextCompat.getDrawable(save.getContext(), R.drawable.ic_password_show_yellow));
                    break;

                case HIDE_BTN:
                    imageButton.setBackground(ContextCompat.getDrawable(save.getContext(), R.drawable.ic_password_hide_yellow));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPasswordState(EditText field, boolean showPassword) {
        try {
            if (showPassword) {
                field.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            field.setSelection(field.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkPasswords() {
        try {
            if (isNullOrEmpty(currentField.getText().toString())) {
                HelperUI.inputWarning(getActivity(), currentField, currentHead, true);
            } else {
                HelperUI.inputDefault(getActivity(), currentField, currentHead, true);
                currentPassword = currentField.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (isNullOrEmpty(passwordField.getText().toString())) {
                HelperUI.inputWarning(getActivity(), passwordField, passwordHead, true);
            } else {
                HelperUI.inputDefault(getActivity(), passwordField, passwordHead, true);
                password = passwordField.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (isNullOrEmpty(confirmField.getText().toString())) {
                HelperUI.inputWarning(getActivity(), confirmField, confirmHead, true);
            } else {
                HelperUI.inputDefault(getActivity(), confirmField, confirmHead, true);
                passwordConfirm = confirmField.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (currentPassword != null && password != null && passwordConfirm != null) {
                if (HelperUI.checkPassword(currentPassword) && HelperUI.checkPassword(password) && HelperUI.checkConfirmPassword(password, passwordConfirm)) {
                    startChangePassword();
                } else {
                    showToast(getString(R.string.not_match_password));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startChangePassword() {
        try {
            presenter.changePassword(new ChangePasswordRequest(currentPassword, password, passwordConfirm));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPasswordChanged() {
        try {
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onChangePasswordResponse(ChangePasswordResponse changePasswordResponse) {
        try {
            if (changePasswordResponse.getStatus() == 0) {
                DialogManager.customErrorDialog(getActivity(), changePasswordResponse.getMessage(), this::onPasswordChanged);
            } else {
                DialogManager.customErrorDialog(getActivity(), changePasswordResponse.getMessage(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attachPresenter() {
        try {
            presenter = ChangePasswordPresenter.getInstance();
            presenter.attachView(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachPresenter() {
        try {
            if (presenter != null) {
                presenter.detachView();
                presenter = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
