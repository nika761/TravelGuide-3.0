package com.example.travelguide.ui.login.fragment;

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
import com.example.travelguide.ui.login.interfaces.ISignUpFragment;
import com.example.travelguide.model.request.SignUpRequest;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.request.CheckMailRequest;
import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.ui.login.presenter.SignUpPresenter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.helper.HelperUI;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SignUpFragment extends Fragment implements ISignUpFragment, View.OnClickListener {

    private static final String TAG = "SignUpFragment";
    private Context context;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private SignUpPresenter signUpPresenter;
    private LinearLayout phoneNumberContainer;
    private String userName, userSurname, nickName, birthDate, email, phoneIndex,
            phoneNumber, password, confirmPassword, nickNameFirst, nickNameSecond;
    private EditText eName, eSurname, eNickName, eMail,
            ePhoneNumber, ePassword, eConfirmPassword;
    private TextView eNameHead, eSurnameHead, eNickNameHead,
            registerBirthDate, registerBirthDateHead,
            eEmailHead, ePhoneNumberHead,
            ePasswordHead, eConfirmPasswordHead,
            signUpBtn, signUpCancelBtn, registerNickOffer,
            registerNickOfferOne, registerNickOfferTwo, terms, policy;
    private long startTime;
    private int color;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

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
        registerBirthDate.setOnClickListener(this);

        registerBirthDateHead = view.findViewById(R.id.register_birth_date_head);
        eMail = view.findViewById(R.id.register_email);
        eEmailHead = view.findViewById(R.id.register_mail_head);
        ePhoneNumber = view.findViewById(R.id.register_phone_number);
        ePhoneNumberHead = view.findViewById(R.id.register_phone_number_head);
        ePassword = view.findViewById(R.id.register_password);
        ePasswordHead = view.findViewById(R.id.register_password_head);
        eConfirmPassword = view.findViewById(R.id.register_confirm_password);
        eConfirmPasswordHead = view.findViewById(R.id.register_confirm_password_head);

        signUpBtn = view.findViewById(R.id.register_sign_btn);
        signUpBtn.setOnClickListener(this);

        signUpCancelBtn = view.findViewById(R.id.register_cancel_btn);
        signUpCancelBtn.setOnClickListener(this);

        registerNickOffer = view.findViewById(R.id.nickName_offer);

        registerNickOfferOne = view.findViewById(R.id.nickName_offer_1);
        registerNickOfferOne.setOnClickListener(this);

        registerNickOfferTwo = view.findViewById(R.id.nickName_offer_2);
        registerNickOfferTwo.setOnClickListener(this);

        terms = view.findViewById(R.id.terms_register);
        terms.setOnClickListener(this);

        policy = view.findViewById(R.id.policy_register);
        policy.setOnClickListener(this);

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

            String dayString = day < 10 ? "0" + day : String.valueOf(day);
            String monthString = month < 10 ? "0" + month : String.valueOf(month);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

//            Date currentDate = new Date();
//            int age = currentDate.getYear() - year;

            int age = currentYear - year;

            if (age < 13) {
                Toast.makeText(context, "18 წელზე მეტის", Toast.LENGTH_SHORT).show();
            } else {
                String date = year + "/" + monthString + "/" + dayString;
                registerBirthDate.setText(date);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                startTime = calendar.getTimeInMillis();
            }
        };

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.color = context.getResources().getColor(R.color.black, null);
    }

    private void onGetData() {

        userName = HelperUI.checkEditTextData(eName, eNameHead, "Name",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        userSurname = HelperUI.checkEditTextData(eSurname, eSurnameHead, "Surname",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        nickName = HelperUI.checkEditTextData(eNickName, eNickNameHead, "NickName",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        email = HelperUI.checkEditTextData(eMail, eEmailHead, "Email",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        if (email != null && HelperUI.checkEmail(email)) {
            HelperUI.setBackgroundDefault(eMail, eEmailHead, "Email",
                    color, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eMail, eEmailHead, "Email", context);
        }

        password = HelperUI.checkEditTextData(ePassword, ePasswordHead, "Password",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        if (password != null && HelperUI.checkPassword(password)) {
            HelperUI.setBackgroundDefault(ePassword, ePasswordHead, "Password",
                    color, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(ePassword, ePasswordHead, "Password", context);
        }

        confirmPassword = HelperUI.checkEditTextData(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                color, HelperUI.BACKGROUND_DEF_BLACK, context);

        if (password != null && confirmPassword != null && HelperUI.checkConfirmPassword(password, confirmPassword)) {
            HelperUI.setBackgroundDefault(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                    color, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",context);
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

            String name = countryCodePicker.getSelectedCountryName();
            String code = countryCodePicker.getSelectedCountryCodeWithPlus();
            phoneIndex = countryCodePicker.getSelectedCountryCode();
            String asds = countryCodePicker.getSelectedCountryEnglishName();
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
            SignUpRequest signUpRequest = new SignUpRequest(userName, userSurname, nickName, email, password,
                    confirmPassword, String.valueOf(startTime), phoneIndex, phoneNumber, String.valueOf(HelperPref.getLanguageId(context)));
            signUpPresenter.sendAuthResponse(signUpRequest);
            //Toast.makeText(getContext(), "Welcome " + userName, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, " Error ", Toast.LENGTH_LONG).show();
        }

    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.c_registration_confirm, null);
        TextView verifyTitle, verifyMessage;

        verifyTitle = customLayout.findViewById(R.id.confirm_title);
        verifyMessage = customLayout.findViewById(R.id.confirm_message);

        verifyTitle.setText(title);
        verifyMessage.setText(message);

        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent, null));
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
        CheckNickRequest checkNickRequest = new CheckNickRequest();
        checkNickRequest.setName(name);
        checkNickRequest.setSurname(surname);
        checkNickRequest.setNickname(nickName);
        signUpPresenter.checkNick(checkNickRequest);
    }

    private void checkEmailToServer(String email) {
        CheckMailRequest checkMailRequest = new CheckMailRequest();
        checkMailRequest.setEmail(email);
        signUpPresenter.checkEmail(checkMailRequest);
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
    public void onGetAuthResult(SignUpResponse signUpResponse) {

        int authResultStatus = signUpResponse.getStatus();

        switch (authResultStatus) {
            case 0:
                showAlertDialog(signUpResponse.getTitle(), signUpResponse.getMessage());
                break;

            case 2:
                HelperUI.setBackgroundWarning(eNickName, eNickNameHead, "NickName",context);
                checkNickNameToServer(userName, userSurname, nickName);
                break;

            case 3:
                Toast.makeText(context, "nickname lenght must be less then 17!", Toast.LENGTH_SHORT).show();
                break;

            case 4:
                HelperUI.setBackgroundWarning(eMail, eEmailHead, "Email",context);
//                checkEmailToServer(email);
                break;

            case 5:
                Toast.makeText(context, "birth date  is not a correct format!", Toast.LENGTH_SHORT).show();
                break;

            case 6:
                Toast.makeText(context, "password lenght must be 8 or more!", Toast.LENGTH_SHORT).show();
                break;

            case 7:
                Toast.makeText(context, "The password confirmation does not match.", Toast.LENGTH_SHORT).show();
                break;

            case 8:
                Toast.makeText(context, "phone lenght must be 9 or more!", Toast.LENGTH_SHORT).show();
                break;

            case 9:
                Toast.makeText(context, "phone index must lenght be 1 or more!", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onGetAuthError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEmailCheckResult(CheckMailResponse checkMailResponse) {
        if (checkMailResponse.getStasus().equals("1")) {
            HelperUI.setBackgroundWarning(eMail, eEmailHead, "Email",context);
        }
    }

    @Override
    public void onGetNickCheckResult(CheckNickResponse checkNickResponse) {
        registerNickOffer.setVisibility(View.VISIBLE);
        registerNickOfferOne.setVisibility(View.VISIBLE);
        registerNickOfferTwo.setVisibility(View.VISIBLE);
        nickNameFirst = checkNickResponse.getNicknames_to_offer().get(0);
        nickNameSecond = checkNickResponse.getNicknames_to_offer().get(1);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_sign_btn:

                onGetData();
                registerNickOffer.setVisibility(View.GONE);
                registerNickOfferOne.setVisibility(View.GONE);
                registerNickOfferTwo.setVisibility(View.GONE);

                break;

            case R.id.register_cancel_btn:

                Objects.requireNonNull(getActivity()).onBackPressed();

                break;

            case R.id.register_birth_date:

                showDatePickerDialog();

                break;

            case R.id.nickName_offer_1:

                eNickName.setText(nickNameFirst);
                HelperUI.setBackgroundDefault(eNickName, eNickNameHead, "NickName",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);

                break;
            case R.id.nickName_offer_2:

                eNickName.setText(nickNameSecond);
                HelperUI.setBackgroundDefault(eNickName, eNickNameHead, "NickName",
                        HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);

                break;

            case R.id.terms_register:

                HelperUI.startTermsAndPolicyActivity(context, HelperUI.TERMS);

                break;

            case R.id.policy_register:

                HelperUI.startTermsAndPolicyActivity(context, HelperUI.POLICY);

                break;

        }
    }
}
