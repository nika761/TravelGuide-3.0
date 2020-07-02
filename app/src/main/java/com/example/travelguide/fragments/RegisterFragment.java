package com.example.travelguide.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.interfaces.IRegisterFragment;
import com.example.travelguide.model.request.RegisterRequestModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.RegisterResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
import com.example.travelguide.presenters.RegisterPresenter;
import com.example.travelguide.utils.UtilsPref;
import com.example.travelguide.utils.UtilsTerms;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.Objects;

public class RegisterFragment extends Fragment implements IRegisterFragment {

    private static final String TAG = "RegisterFragment";
    private Context context;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private RegisterPresenter registerPresenter;
    private LinearLayout phoneNumberContainer;
    private String userName, userSurname, nickName, birthDate, email, emailForCheck,
            phoneNumber, password, passwordForCheck, confirmPassword, nickNameFirst, nickNameSecond;
    private EditText registerName, registerSurname, registerNickName, registerEmail,
            registerPhoneNumber, registerPassword, registerConfirmPassword;
    private TextView registerNameHead, registerSurnameHead, registerNickNameHead,
            registerBirthDate, registerBirthDateHead,
            registerEmailHead, registerPhoneNumberHead,
            registerPasswordHead, registerConfirmPasswordHead,
            registerSignUpTxt, registerCancelTxt, registerNickOffer, registerNickOfferOne, registerNickOfferTwo, terms, policy;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniUI(view);
        setClickListeners();
    }

    private void setClickListeners() {

        registerSignUpTxt.setOnClickListener(v -> {
            onGetData();
            registerNickOffer.setVisibility(View.GONE);
            registerNickOfferOne.setVisibility(View.GONE);
            registerNickOfferTwo.setVisibility(View.GONE);
        });

        registerCancelTxt.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        registerBirthDate.setOnClickListener(v -> showDatePickerDialog());

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

            String dayString = day < 10 ? "0" + day : String.valueOf(day);
            String monthString = month < 10 ? "0" + month : String.valueOf(month);

            String date = year + "/" + monthString + "/" + dayString;
            registerBirthDate.setText(date);
        };

//        registerEmail.setOnFocusChangeListener((v, hasFocus) -> {
//            if (!hasFocus && registerEmail.getText() != null) {
//                CheckMailRequestModel checkMailRequestModel = new CheckMailRequestModel();
//                checkMailRequestModel.setEmail(registerEmail.getText().toString());
//                registerPresenter.checkEmail(checkMailRequestModel);
//            }
//        });

        registerNickOfferOne.setOnClickListener(v -> {
            registerNickName.setText(nickNameFirst);
            setFieldsDefault(registerNickName, registerNickNameHead, " NickName ");
        });

        registerNickOfferTwo.setOnClickListener(v -> {
            registerNickName.setText(nickNameSecond);
            setFieldsDefault(registerNickName, registerNickNameHead, " NickName ");
        });

        terms.setOnClickListener(v -> UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.TERMS));

        policy.setOnClickListener(v -> UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.POLICY));

    }

    private String checkEditTextData(EditText currentField, TextView currentHead, String currentHeadText, String currentStringData) {

        if (currentField.getText().toString().isEmpty()) {
            setFieldsWarning(currentField, currentHead, currentHeadText);
        } else {
            setFieldsDefault(currentField, currentHead, currentHeadText);
            currentStringData = currentField.getText().toString();
        }
        return currentStringData;
    }

    private void setFieldsWarning(EditText currentField, TextView currentHead, String currentHeadText) {
        currentField.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
        currentHead.setText(String.format("* %s", currentHeadText));
        currentHead.setTextColor(getResources().getColor(R.color.red));
        YoYo.with(Techniques.Shake)
                .duration(300)
                .playOn(currentField);
    }

    private void setFieldsDefault(EditText currentField, TextView currentHead, String currentHeadText) {
        currentField.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
        currentHead.setText(currentHeadText);
        currentHead.setTextColor(getResources().getColor(R.color.black));
    }

    private void onGetData() {

        userName = checkEditTextData(registerName, registerNameHead, "Name", userName);
        userSurname = checkEditTextData(registerSurname, registerSurnameHead, "Surname", userSurname);
        nickName = checkEditTextData(registerNickName, registerNickNameHead, "NickName", nickName);
        emailForCheck = checkEditTextData(registerEmail, registerEmailHead, "Email", emailForCheck);
        if (emailForCheck != null) {
            if (!checkEmail(emailForCheck)) {
                setFieldsWarning(registerEmail, registerEmailHead, "Email");
            } else {
                setFieldsDefault(registerEmail, registerEmailHead, "Email");
                email = emailForCheck;
            }
        }

        if (registerPhoneNumber.getText().toString().isEmpty() || !checkNumber(registerPhoneNumber)) {
            phoneNumberContainer.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerPhoneNumberHead.setText("* Phone Number ");
            registerPhoneNumberHead.setTextColor(getResources().getColor(R.color.red));
//            StyleableToast.makeText(getContext(), " Number is incorrect !", Toast.LENGTH_LONG, R.style.errorToast).show();
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(phoneNumberContainer);
        } else {
            phoneNumberContainer.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerPhoneNumberHead.setText(" Phone Number ");
            registerPhoneNumberHead.setTextColor(getResources().getColor(R.color.black));
            phoneNumber = registerPhoneNumber.getText().toString();
            //registerPhoneNumber.getText().clear();
        }

        if (registerBirthDate.getText().toString().isEmpty()) {
            registerBirthDate.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerBirthDateHead.setText("* Birth Date ");
            registerBirthDateHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerBirthDate);
        } else {
            registerBirthDate.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerBirthDateHead.setText(" Birth Date ");
            registerBirthDateHead.setTextColor(getResources().getColor(R.color.black));
            birthDate = registerBirthDate.getText().toString();
        }

        passwordForCheck = checkEditTextData(registerPassword, registerPasswordHead, "Password", passwordForCheck);
        if (passwordForCheck != null) {
            if (!checkPassword(passwordForCheck)) {
                setFieldsWarning(registerPassword, registerPasswordHead, "Password");
            } else {
                setFieldsDefault(registerPassword, registerPasswordHead, "Password");
                password = passwordForCheck;
            }
        }

        if (registerConfirmPassword.getText().toString().isEmpty() || !confirmPassword(registerPassword, registerConfirmPassword)) {
            setFieldsWarning(registerConfirmPassword, registerConfirmPasswordHead, "Confirm Password");
        } else {
            setFieldsDefault(registerConfirmPassword, registerConfirmPasswordHead, "Confirm Password");
            confirmPassword = registerConfirmPassword.getText().toString();
        }

        if (userName != null &&
                userSurname != null &&
                nickName != null &&
                email != null &&
                phoneNumber != null &&
                birthDate != null &&
                password != null &&
                confirmPassword != null) {
            String languageId = String.valueOf(UtilsPref.getLanguageId(context));
            RegisterRequestModel registerRequestModel = new RegisterRequestModel(userName, userSurname, nickName, email, password,
                    confirmPassword, birthDate, phoneNumber, languageId);
            registerPresenter.sendAuthResponse(registerRequestModel);
            //Toast.makeText(getContext(), "Welcome " + userName, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), " Error ", Toast.LENGTH_LONG).show();
        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.registration_confirm, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent_background));
        dialog.show();

    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getContext()),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean checkEmail(String enteredEmail) {
        boolean emailValidate = false;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = enteredEmail.trim();

        if (email.matches(emailPattern)) {
            emailValidate = true;
        }
        return emailValidate;
    }

    private boolean checkNumber(EditText enteredNumber) {
        boolean numberValidate = false;

        countryCodePicker.registerCarrierNumberEditText(enteredNumber);

        if (countryCodePicker.isValidFullNumber()) {
            numberValidate = true;
        }
        return numberValidate;
    }

    private boolean checkPassword(String enteredPassword) {
        boolean passwordValidate = true;

        if (enteredPassword.trim().length() < 8) {
            passwordValidate = false;
        }
        return passwordValidate;
    }

    private boolean confirmPassword(EditText password, EditText confirmPassword) {
        boolean confirmPassowrdValidate = false;

        if (confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPassowrdValidate = true;
        }
        return confirmPassowrdValidate;
    }

    private void checkNickNameToServer(String name, String surname, String nickName) {
        CheckNickRequestModel checkNickRequestModel = new CheckNickRequestModel();
        checkNickRequestModel.setName(name);
        checkNickRequestModel.setSurname(surname);
        checkNickRequestModel.setNickname(nickName);
        registerPresenter.checkNick(checkNickRequestModel);
    }

    private void checkEmailToServer(String email) {
        CheckMailRequestModel checkMailRequestModel = new CheckMailRequestModel();
        checkMailRequestModel.setEmail(email);
        registerPresenter.checkEmail(checkMailRequestModel);
    }

    private void iniUI(View view) {
        context = getContext();
        registerPresenter = new RegisterPresenter(this);
        countryCodePicker = view.findViewById(R.id.ccp);
        phoneNumberContainer = view.findViewById(R.id.register_phone_number_container);
        registerName = view.findViewById(R.id.register_name);
        registerNameHead = view.findViewById(R.id.register_name_head);
        registerSurname = view.findViewById(R.id.register_surname);
        registerSurnameHead = view.findViewById(R.id.register_surname_head);
        registerNickName = view.findViewById(R.id.register_nick_name);
        registerNickNameHead = view.findViewById(R.id.register_nick_name_head);
        registerBirthDate = view.findViewById(R.id.register_birth_date);
        registerBirthDateHead = view.findViewById(R.id.register_birth_date_head);
        registerEmail = view.findViewById(R.id.register_email);
        registerEmailHead = view.findViewById(R.id.register_mail_head);
        registerPhoneNumber = view.findViewById(R.id.register_phone_number);
        registerPhoneNumberHead = view.findViewById(R.id.register_phone_number_head);
        registerPassword = view.findViewById(R.id.register_password);
        registerPasswordHead = view.findViewById(R.id.register_password_head);
        registerConfirmPassword = view.findViewById(R.id.register_confirm_password);
        registerConfirmPasswordHead = view.findViewById(R.id.register_confirm_password_head);
        registerSignUpTxt = view.findViewById(R.id.register_sign_btn);
        registerCancelTxt = view.findViewById(R.id.register_cancel_btn);
        registerNickOffer = view.findViewById(R.id.nickName_offer);
        registerNickOfferOne = view.findViewById(R.id.nickName_offer_1);
        registerNickOfferTwo = view.findViewById(R.id.nickName_offer_2);
        terms = view.findViewById(R.id.terms_register);
        policy = view.findViewById(R.id.policy_register);
    }

    private void keyBoardFocusHelper(View view) {
        view.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onGetAuthResult(RegisterResponseModel registerResponseModel) {

        String authResultStatus = registerResponseModel.getStatus();

        switch (authResultStatus) {
            case "0":
                showAlertDialog();
                break;

            case "2":
                setFieldsWarning(registerNickName, registerNickNameHead, "NickName");
                checkNickNameToServer(userName, userSurname, nickName);
                break;

            case "4":
                setFieldsWarning(registerEmail, registerEmailHead, "Email");
//                checkEmailToServer(email);
                break;

        }
    }

    @Override
    public void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel) {
        if (checkMailResponseModel.getStasus().equals("1")) {
            setFieldsWarning(registerEmail, registerEmailHead, "Email");
        }
    }

    @Override
    public void onGetNickCheckResult(CheckNickResponseModel checkNickResponseModel) {
        registerNickOffer.setVisibility(View.VISIBLE);
        registerNickOfferOne.setVisibility(View.VISIBLE);
        registerNickOfferTwo.setVisibility(View.VISIBLE);
        nickNameFirst = checkNickResponseModel.getNicknames_to_offer().get(0);
        nickNameSecond = checkNickResponseModel.getNicknames_to_offer().get(1);

        registerNickOfferOne.setText(nickNameFirst);
        registerNickOfferTwo.setText(nickNameSecond);

    }

    @Override
    public void onDestroy() {
        if (registerPresenter != null) {
            registerPresenter = null;
        }
        super.onDestroy();
    }

}
