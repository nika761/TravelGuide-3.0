package com.example.travelguide.ui.login.signUp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperClients;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.request.SignUpRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.helper.HelperUI;
import com.hbb20.CountryCodePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity implements SignUpListener, View.OnClickListener {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private SignUpPresenter signUpPresenter;

    private String photoUrl, userName, userSurname, nickName, birthDate, phoneIndex, phoneNumber, nickNameFirst, nickNameSecond;
    private final static int PICK_IMAGE = 29;
    private File profilePhotoFile;
    private long timeStamp;
    private int blackColor, gender;

    private LinearLayout phoneNumberContainer;
    private EditText eName, eSurname, eNickName, eMail, ePhoneNumber, ePassword, eConfirmPassword;
    private TextView eNameHead, eSurnameHead, eNickNameHead, registerBirthDate, registerBirthDateHead, eEmailHead,
            ePhoneNumberHead, ePasswordHead, eConfirmPasswordHead, registerNickOffer, registerNickOfferOne,
            registerNickOfferTwo;
    private LottieAnimationView loader;
    private CircleImageView profileImage;
    private FrameLayout loaderBackground;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        this.blackColor = getResources().getColor(R.color.black, null);

        signUpPresenter = new SignUpPresenter(this);

        loader = findViewById(R.id.loader_sign_up);
        loaderBackground = findViewById(R.id.bg_loader_sign_up);
        countryCodePicker = findViewById(R.id.ccp);
        phoneNumberContainer = findViewById(R.id.register_phone_number_container);
        eName = findViewById(R.id.register_name);
        eNameHead = findViewById(R.id.register_name_head);
        eSurname = findViewById(R.id.register_surname);
        eSurnameHead = findViewById(R.id.register_surname_head);
        eNickName = findViewById(R.id.register_nick_name);
        eNickNameHead = findViewById(R.id.register_nick_name_head);
        registerBirthDateHead = findViewById(R.id.register_birth_date_head);
        eMail = findViewById(R.id.register_email);
        eEmailHead = findViewById(R.id.register_mail_head);
        ePhoneNumber = findViewById(R.id.register_phone_number);
        ePhoneNumberHead = findViewById(R.id.register_phone_number_head);
        ePassword = findViewById(R.id.register_password);
        ePasswordHead = findViewById(R.id.register_password_head);
        eConfirmPassword = findViewById(R.id.register_confirm_password);
        eConfirmPasswordHead = findViewById(R.id.register_confirm_password_head);
        registerNickOffer = findViewById(R.id.nickName_offer);
        profileImage = findViewById(R.id.register_photo);

        registerBirthDate = findViewById(R.id.register_birth_date);
        registerBirthDate.setOnClickListener(this);

        registerNickOfferOne = findViewById(R.id.nickName_offer_1);
        registerNickOfferOne.setOnClickListener(this);

        registerNickOfferTwo = findViewById(R.id.nickName_offer_2);
        registerNickOfferTwo.setOnClickListener(this);

        View uploadImage = findViewById(R.id.register_upload_photo);
        uploadImage.setOnClickListener(this);

        TextView signUpBtn = findViewById(R.id.register_sign_btn);
        signUpBtn.setOnClickListener(this);

        TextView signUpCancelBtn = findViewById(R.id.register_cancel_btn);
        signUpCancelBtn.setOnClickListener(this);

        TextView terms = findViewById(R.id.terms_register);
        terms.setOnClickListener(this);

        TextView policy = findViewById(R.id.policy_register);
        policy.setOnClickListener(this);

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String dayString = day < 10 ? "0" + day : String.valueOf(day);
            String monthString = month < 10 ? "0" + month : String.valueOf(month);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - year;

            if (age < 13) {
                Toast.makeText(this, "Application age restriction 13+", Toast.LENGTH_SHORT).show();
            } else {
                String date = year + "/" + monthString + "/" + dayString;
                registerBirthDate.setText(date);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                timeStamp = calendar.getTimeInMillis();
            }
        };

        RadioGroup genderGroup = findViewById(R.id.radio_group);
        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {

                case R.id.radio_male:
                    gender = 0;
                    Log.e("gender", String.valueOf(gender));
                    break;

                case R.id.radio_female:
                    gender = 1;
                    Log.e("gender", String.valueOf(gender));
                    break;

                case R.id.radio_other:
                    gender = 2;
                    break;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        pickProfileImage(uri);
                    }
                }
            }
        }
    }

    public void pickProfileImage(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            profilePhotoFile = new File(picturePath);
            signUpPresenter.uploadToS3(HelperClients.transferObserver(this, profilePhotoFile));
            HelperMedia.loadCirclePhoto(this, picturePath, profileImage);
        }
    }

    public void loadingVisibility(boolean visible) {
        if (visible) {
            loaderBackground.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
        } else {
            loaderBackground.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSignUpResponse(SignUpResponse signUpResponse) {
        int authResultStatus = signUpResponse.getStatus();

        loadingVisibility(false);

        switch (authResultStatus) {
            case 0:
                showConfirmDialog(signUpResponse.getTitle(), signUpResponse.getMessage());
                break;

            case 2:
                HelperUI.setBackgroundWarning(eNickName, eNickNameHead, "NickName", this);
                signUpPresenter.checkNick(new CheckNickRequest(nickName, userName, userSurname));
                break;

            case 3:
                Toast.makeText(this, "nickname lenght must be less then 17!", Toast.LENGTH_SHORT).show();
                break;

            case 4:
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
                HelperUI.setBackgroundWarning(eMail, eEmailHead, "Email", this);
                break;

            case 5:
                Toast.makeText(this, "birth date  is not a correct format!", Toast.LENGTH_SHORT).show();
                break;

            case 6:
                Toast.makeText(this, "password lenght must be 8 or more!", Toast.LENGTH_SHORT).show();
                break;

            case 7:
                Toast.makeText(this, "The password confirmation does not match.", Toast.LENGTH_SHORT).show();
                break;

            case 8:
                Toast.makeText(this, "phone lenght must be 9 or more!", Toast.LENGTH_SHORT).show();
                break;

            case 9:
                Toast.makeText(this, "phone index must lenght be 1 or more!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhotoUploadSuccess() {
        photoUrl = HelperClients.amazonS3Client(this).getResourceUrl(HelperClients.S3_BUCKET, profilePhotoFile.getName());
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_upload_photo:

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE);

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;

            case R.id.register_sign_btn:
                onGetData();
                registerNickOffer.setVisibility(View.GONE);
                registerNickOfferOne.setVisibility(View.GONE);
                registerNickOfferTwo.setVisibility(View.GONE);
                break;

            case R.id.register_cancel_btn:
                finish();
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
                HelperUI.startWebActivity(this, HelperUI.TERMS);
                break;

            case R.id.policy_register:
                HelperUI.startWebActivity(this, HelperUI.POLICY);
                break;

        }
    }


    private boolean checkNumber(EditText enteredNumber) {
        boolean numberValidate = false;

        countryCodePicker.registerCarrierNumberEditText(enteredNumber);
        if (countryCodePicker.isValidFullNumber()) {
            numberValidate = true;
        }
        return numberValidate;
    }

    private void onGetData() {

        if (gender != 0) {
            Toast.makeText(this, "Please enter gender", Toast.LENGTH_SHORT).show();
        }

        userName = HelperUI.checkEditTextData(eName, eNameHead, "Name",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        userSurname = HelperUI.checkEditTextData(eSurname, eSurnameHead, "Surname",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        nickName = HelperUI.checkEditTextData(eNickName, eNickNameHead, "NickName",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        String email = HelperUI.checkEditTextData(eMail, eEmailHead, "Email",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        if (email != null && HelperUI.checkEmail(email)) {
            HelperUI.setBackgroundDefault(eMail, eEmailHead, "Email",
                    blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eMail, eEmailHead, "Email", this);
        }

        String password = HelperUI.checkEditTextData(ePassword, ePasswordHead, "Password",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        if (password != null && HelperUI.checkPassword(password)) {
            HelperUI.setBackgroundDefault(ePassword, ePasswordHead, "Password",
                    blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(ePassword, ePasswordHead, "Password", this);
        }

        String confirmPassword = HelperUI.checkEditTextData(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        if (password != null && confirmPassword != null && HelperUI.checkConfirmPassword(password, confirmPassword)) {
            HelperUI.setBackgroundDefault(eConfirmPassword, eConfirmPasswordHead, "Confirm Password",
                    blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eConfirmPassword, eConfirmPasswordHead, "Confirm Password", this);
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

        //to do
//        if (photoUrl != null)

        if (userName != null &&
                userSurname != null &&
                nickName != null &&
                email != null &&
                phoneNumber != null &&
                birthDate != null &&
                password != null &&
                confirmPassword != null) {
            loadingVisibility(true);
            SignUpRequest signUpRequest = new SignUpRequest(userName, userSurname, nickName, email, password,
                    confirmPassword, String.valueOf(timeStamp), phoneIndex, photoUrl, phoneNumber,
                    String.valueOf(HelperPref.getLanguageId(this)), gender);
            signUpPresenter.signUp(signUpRequest);
            //Toast.makeText(getContext(), "Welcome " + userName, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, " Error ", Toast.LENGTH_LONG).show();
        }

    }

    private void showConfirmDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.c_registration_confirm, null);
        TextView verifyTitle, verifyMessage;

        verifyTitle = customLayout.findViewById(R.id.confirm_title);
        verifyMessage = customLayout.findViewById(R.id.confirm_message);

        verifyTitle.setText(title);
        verifyMessage.setText(message);

        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent, null));
        }

        dialog.show();
    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if (signUpPresenter != null) {
            signUpPresenter = null;
        }
        super.onDestroy();
    }
}
