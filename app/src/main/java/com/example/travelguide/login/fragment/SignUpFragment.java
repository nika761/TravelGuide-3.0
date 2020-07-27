package com.example.travelguide.login.fragment;

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
import com.example.travelguide.helper.HelperFields;
import com.example.travelguide.login.interfaces.ISignUpFragment;
import com.example.travelguide.model.request.SignUpRequestModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.SignUpResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
import com.example.travelguide.login.presenter.SignUpPresenter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.helper.HelperUI;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.Objects;

public class SignUpFragment extends Fragment implements ISignUpFragment {

    private static final String TAG = "SignUpFragment";
    private Context context;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private SignUpPresenter signUpPresenter;
    private LinearLayout phoneNumberContainer;
    private String userName, userSurname, nickName, birthDate, email,
            phoneNumber, password, confirmPassword, nickNameFirst, nickNameSecond;
    private EditText eName, eSurname, eNickName, eMail,
            ePhoneNumber, ePassword, eConfirmPassword;
    private TextView eNameHead, eSurnameHead, eNickNameHead,
            registerBirthDate, registerBirthDateHead,
            eEmailHead, ePhoneNumberHead,
            ePasswordHead, eConfirmPasswordHead,
            registerSignUpTxt, registerCancelTxt, registerNickOffer,
            registerNickOfferOne, registerNickOfferTwo, terms, policy;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
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
//                signUpPresenter.checkEmail(checkMailRequestModel);
//            }
//        });

        registerNickOfferOne.setOnClickListener(v -> {
            eNickName.setText(nickNameFirst);
            HelperFields.setFieldsDefault(eNickName, eNickNameHead, "NickName",
                    HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);
        });

        registerNickOfferTwo.setOnClickListener(v -> {
            eNickName.setText(nickNameSecond);
            HelperFields.setFieldsDefault(eNickName, eNickNameHead, "NickName",
                    HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);
        });

        terms.setOnClickListener(v -> HelperUI.startTermsAndPolicyActivity(context, HelperUI.TERMS));

        policy.setOnClickListener(v -> HelperUI.startTermsAndPolicyActivity(context, HelperUI.POLICY));

    }

    private void onGetData() {

        userName = HelperFields.checkEditTextData(eName, eNameHead, "Name",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        userSurname = HelperFields.checkEditTextData(eSurname, eSurnameHead, "Surname",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        nickName = HelperFields.checkEditTextData(eNickName, eNickNameHead, "NickName",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        email = HelperFields.checkEditTextData(eMail, eEmailHead, "Email",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        if (email != null && HelperFields.checkEmail(email)) {
            HelperFields.setFieldsDefault(eMail, eEmailHead, "Email",
                    HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);
        } else {
            HelperFields.setFieldsWarning(eMail, eEmailHead, "Email");
        }

        password = HelperFields.checkEditTextData(ePassword, ePasswordHead, "Password",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        if (password != null && HelperFields.checkPassword(password)) {
            HelperFields.setFieldsDefault(ePassword, ePasswordHead, "Password",
                    HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);
        } else {
            HelperFields.setFieldsWarning(ePassword, ePasswordHead, "Password");
        }

        confirmPassword = HelperFields.checkEditTextData(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);

        if (password != null && confirmPassword != null && HelperFields.confirmPassword(password, confirmPassword)) {
            HelperFields.setFieldsDefault(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                    HelperFields.BLACK, HelperFields.BACKGROUND_DEF_BLACK);
        } else {
            HelperFields.setFieldsWarning(eConfirmPassword, eConfirmPasswordHead, "Confirm Password");
        }

        if (ePhoneNumber.getText().toString().isEmpty() || !checkNumber(ePhoneNumber)) {
            phoneNumberContainer.setBackground(getResources().getDrawable(R.drawable.bg_fields_warning));
            ePhoneNumberHead.setText("* Phone Number ");
            ePhoneNumberHead.setTextColor(getResources().getColor(R.color.red));
//            StyleableToast.makeText(getContext(), " Number is incorrect !", Toast.LENGTH_LONG, R.style.errorToast).show();
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(phoneNumberContainer);
        } else {
            phoneNumberContainer.setBackground(getResources().getDrawable(R.drawable.bg_signup_fields));
            ePhoneNumberHead.setText(" Phone Number ");
            ePhoneNumberHead.setTextColor(getResources().getColor(R.color.black));
            phoneNumber = ePhoneNumber.getText().toString();
            //registerPhoneNumber.getText().clear();
        }

        if (registerBirthDate.getText().toString().isEmpty()) {
            registerBirthDate.setBackground(getResources().getDrawable(R.drawable.bg_fields_warning));
            registerBirthDateHead.setText("* Birth Date ");
            registerBirthDateHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerBirthDate);
        } else {
            registerBirthDate.setBackground(getResources().getDrawable(R.drawable.bg_signup_fields));
            registerBirthDateHead.setText(" Birth Date ");
            registerBirthDateHead.setTextColor(getResources().getColor(R.color.black));
            birthDate = registerBirthDate.getText().toString();
        }

        if (userName != null &&
                userSurname != null &&
                nickName != null &&
                email != null &&
                phoneNumber != null &&
                birthDate != null &&
                password != null &&
                confirmPassword != null) {
            SignUpRequestModel signUpRequestModel = new SignUpRequestModel(userName, userSurname, nickName, email, password,
                    confirmPassword, birthDate, phoneNumber, String.valueOf(HelperPref.getLanguageId(context)));
            signUpPresenter.sendAuthResponse(signUpRequestModel);
            //Toast.makeText(getContext(), "Welcome " + userName, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), " Error ", Toast.LENGTH_LONG).show();
        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.c_registration_confirm, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
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

    private boolean checkNumber(EditText enteredNumber) {
        boolean numberValidate = false;

        countryCodePicker.registerCarrierNumberEditText(enteredNumber);
        if (countryCodePicker.isValidFullNumber()) {
            numberValidate = true;
        }
        return numberValidate;
    }

    private void checkNickNameToServer(String name, String surname, String nickName) {
        CheckNickRequestModel checkNickRequestModel = new CheckNickRequestModel();
        checkNickRequestModel.setName(name);
        checkNickRequestModel.setSurname(surname);
        checkNickRequestModel.setNickname(nickName);
        signUpPresenter.checkNick(checkNickRequestModel);
    }

    private void checkEmailToServer(String email) {
        CheckMailRequestModel checkMailRequestModel = new CheckMailRequestModel();
        checkMailRequestModel.setEmail(email);
        signUpPresenter.checkEmail(checkMailRequestModel);
    }

    private void iniUI(View view) {
        context = getContext();
        signUpPresenter = new SignUpPresenter(this);
        countryCodePicker = view.findViewById(R.id.ccp);
        phoneNumberContainer = view.findViewById(R.id.register_phone_number_container);
        eName = view.findViewById(R.id.register_name);
        eNameHead = view.findViewById(R.id.register_name_head);
        eSurname = view.findViewById(R.id.register_surname);
        eSurnameHead = view.findViewById(R.id.register_surname_head);
        eNickName = view.findViewById(R.id.register_nick_name);
        eNickNameHead = view.findViewById(R.id.register_nick_name_head);
        registerBirthDate = view.findViewById(R.id.register_birth_date);
        registerBirthDateHead = view.findViewById(R.id.register_birth_date_head);
        eMail = view.findViewById(R.id.register_email);
        eEmailHead = view.findViewById(R.id.register_mail_head);
        ePhoneNumber = view.findViewById(R.id.register_phone_number);
        ePhoneNumberHead = view.findViewById(R.id.register_phone_number_head);
        ePassword = view.findViewById(R.id.register_password);
        ePasswordHead = view.findViewById(R.id.register_password_head);
        eConfirmPassword = view.findViewById(R.id.register_confirm_password);
        eConfirmPasswordHead = view.findViewById(R.id.register_confirm_password_head);
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
    public void onGetAuthResult(SignUpResponseModel signUpResponseModel) {

        String authResultStatus = signUpResponseModel.getStatus();

        switch (authResultStatus) {
            case "0":
                showAlertDialog();
                break;

            case "2":
                HelperFields.setFieldsWarning(eNickName, eNickNameHead, "NickName");
                checkNickNameToServer(userName, userSurname, nickName);
                break;

            case "4":
                HelperFields.setFieldsWarning(eMail, eEmailHead, "Email");
//                checkEmailToServer(email);
                break;

        }
    }

    @Override
    public void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel) {
        if (checkMailResponseModel.getStasus().equals("1")) {
            HelperFields.setFieldsWarning(eMail, eEmailHead, "Email");
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
        if (signUpPresenter != null) {
            signUpPresenter = null;
        }
        super.onDestroy();
    }

}
