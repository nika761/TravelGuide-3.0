package com.example.travelguide.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.interfaces.FragmentClickActions;
import com.example.travelguide.utils.Utils;
import com.hbb20.CountryCodePicker;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Calendar;
import java.util.Objects;

public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";

    private Context context;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private LinearLayout phoneNumberContainer;
    private String userName, userSurname, nickName, birthDate, email,
            phoneNumber, password, confirmPassword;
    private FragmentClickActions fragmentClickActions;
    private EditText registerName, registerSurname, registerNickName, registerEmail,
            registerPhoneNumber, registerPassword, registerConfirmPassword;
    private TextView registerNameHead, registerSurnameHead, registerNickNameHead,
            registerBirthDate, registerBirthDateHead,
            registerEmailHead, registerPhoneNumberHead,
            registerPasswordHead, registerConfirmPasswordHead,
            registerSignUpTxt, registerCancelTxt;

    public RegisterFragment(FragmentClickActions fragmentClickActions) {
        this.fragmentClickActions = fragmentClickActions;
    }

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
            checkEnteredDate();
        });

        registerCancelTxt.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).onBackPressed();
            fragmentClickActions.backToSignInFragment();
        });

        registerBirthDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
            String date = month + "/" + day + "/" + year;
            registerBirthDate.setText(date);
        };
    }

    private void checkEnteredDate() {

        if (registerName.getText().toString().isEmpty()) {
            registerName.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerNameHead.setText("* Name ");
            registerNameHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerName);

        } else {
            registerName.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerNameHead.setText(" Name ");
            registerNameHead.setTextColor(getResources().getColor(R.color.black));
            userName = registerName.getText().toString();
            //registerUserName.getText().clear();
        }

        if (registerSurname.getText().toString().isEmpty()) {
            registerSurname.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerSurnameHead.setText("* UserName ");
            registerSurnameHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerSurname);

        } else {
            registerSurname.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerSurnameHead.setText(" UserName ");
            registerSurnameHead.setTextColor(getResources().getColor(R.color.black));
            userSurname = registerSurname.getText().toString();
            //registerUserName.getText().clear();
        }


        if (registerNickName.getText().toString().isEmpty()) {
            registerNickName.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerNickNameHead.setText("* Nick Name ");
            registerNickNameHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerNickName);
        } else {
            registerNickName.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerNickNameHead.setText(" Nick Name ");
            registerNickNameHead.setTextColor(getResources().getColor(R.color.black));
            nickName = registerNickName.getText().toString();
            //registerNickName.getText().clear();
        }

        if (registerEmail.getText().toString().isEmpty() || !checkEmail(registerEmail)) {
            registerEmail.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerEmailHead.setText("* Email ");
            registerEmailHead.setTextColor(getResources().getColor(R.color.red));
            StyleableToast.makeText(getContext(), " Email is incorrect !", Toast.LENGTH_LONG, R.style.errorToast).show();
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerEmail);
        } else {
            registerEmail.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerEmailHead.setText(" Email ");
            registerEmailHead.setTextColor(getResources().getColor(R.color.black));
            email = registerEmail.getText().toString();
            //registerEmail.getText().clear();
        }

        if (registerPhoneNumber.getText().toString().isEmpty() || !checkNumber(registerPhoneNumber)) {
            phoneNumberContainer.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerPhoneNumberHead.setText("* Phone Number ");
            registerPhoneNumberHead.setTextColor(getResources().getColor(R.color.red));
            StyleableToast.makeText(getContext(), " Number is incorrect !", Toast.LENGTH_LONG, R.style.errorToast).show();
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

        if (registerPassword.getText().toString().isEmpty()) {
            registerPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerPasswordHead.setText("* Password ");
            registerPasswordHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerPassword);
        } else {
            registerPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerPasswordHead.setText(" Password ");
            registerPasswordHead.setTextColor(getResources().getColor(R.color.black));
            password = registerPassword.getText().toString();
            //registerPassword.getText().clear();

        }

        if (registerConfirmPassword.getText().toString().isEmpty()) {
            registerConfirmPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittext_worning));
            registerConfirmPasswordHead.setText("* Confirm Password ");
            registerConfirmPasswordHead.setTextColor(getResources().getColor(R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerConfirmPassword);
        } else {
            registerConfirmPassword.setBackground(getResources().getDrawable(R.drawable.background_signup_edittexts));
            registerConfirmPasswordHead.setText(" Confirm Password ");
            registerConfirmPasswordHead.setTextColor(getResources().getColor(R.color.black));
            confirmPassword = registerConfirmPassword.getText().toString();
            //registerConfirmPassword.getText().clear();
        }

        if (userName != null && userSurname != null && nickName != null && email != null
                && phoneNumber != null && birthDate != null && password != null && confirmPassword != null) {

            showAlertDialog();
            //Toast.makeText(getContext(), "Welcome " + userName, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getContext(), "Please fill all tabs " , Toast.LENGTH_LONG).show();
//                new StyleableToast
////                        .Builder(Objects.requireNonNull(getContext()))
////                        .text(" Please fill all tabs !")
////                        .textColor(Color.RED)
////                        .textBold()
////                        .textSize(14)
////                        .backgroundColor(Color.WHITE)
////                        .solidBackground()
////                        .stroke(1, Color.BLACK)
////                        .show();
//            StyleableToast.makeText(Objects.requireNonNull(getContext()),
//                    " Please fill all tabs !", Toast.LENGTH_LONG, R.style.errorToast).show();
        }

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.registration_confirm, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
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

    private boolean checkEmail(EditText enteredEmail) {
        boolean emailValidate = false;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = enteredEmail.getText().toString().trim();

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

    private void iniUI(View view) {
        context = getContext();
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
}
