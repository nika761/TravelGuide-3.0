package travelguideapp.ge.travelguide.ui.login.signUp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperDate;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.request.SignUpRequest;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.helper.HelperUI;

import com.hbb20.CountryCodePicker;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;

public class SignUpActivity extends BaseActivity implements SignUpListener {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private SignUpPresenter signUpPresenter;
    private File profilePhotoFile;

    private String photoUrl, userName, userSurname, nickName, birthDate, phoneIndex, phoneNumber, nickNameFirst, nickNameSecond;
    private int blackColor, gender = 3;
    private long timeStamp;
    private boolean genderChecked = false;

    private EditText eName, eSurname, eNickName, eMail, ePhoneNumber, ePassword, eConfirmPassword;
    private TextView eNameHead, eSurnameHead, eNickNameHead, registerBirthDate, registerBirthDateHead, eEmailHead,
            ePhoneNumberHead, ePasswordHead, eConfirmPasswordHead, registerNickOffer, registerNickOfferOne,
            registerNickOfferTwo;
    private LinearLayout phoneNumberContainer;
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
        this.blackColor = ContextCompat.getColor(this, R.color.black);

        signUpPresenter = new SignUpPresenter(this);

        loader = findViewById(R.id.loader_sign_up);
        loaderBackground = findViewById(R.id.bg_loader_sign_up);
        countryCodePicker = findViewById(R.id.number_picker);
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
        registerBirthDate.setOnClickListener(v -> DialogManager.datePickerDialog(SignUpActivity.this, mDateSetListener, 0));

        registerNickOfferOne = findViewById(R.id.nickName_offer_1);
        registerNickOfferOne.setOnClickListener(v -> setNickName(1));

        registerNickOfferTwo = findViewById(R.id.nickName_offer_2);
        registerNickOfferTwo.setOnClickListener(v -> setNickName(2));

        View uploadImage = findViewById(R.id.register_upload_photo);
        uploadImage.setOnClickListener(v -> checkPermissionAndStartPick());

        TextView signUpBtn = findViewById(R.id.register_sign_btn);
        signUpBtn.setOnClickListener(v -> startSignUp());

        TextView signUpCancelBtn = findViewById(R.id.register_cancel_btn);
        signUpCancelBtn.setOnClickListener(v -> finish());

        TextView terms = findViewById(R.id.terms_register);
        terms.setOnClickListener(v -> HelperUI.startWebActivity(SignUpActivity.this, LoadWebViewBy.TERMS, ""));

        TextView policy = findViewById(R.id.policy_register);
        policy.setOnClickListener(v -> HelperUI.startWebActivity(SignUpActivity.this, LoadWebViewBy.POLICY, ""));

        mDateSetListener = (datePicker, year, month, day) -> {
            try {
                if (HelperDate.checkAgeOfUser(year, month + 1, day)) {
                    registerBirthDate.setText(HelperDate.getDateStringFormat(year, month, day));
                    timeStamp = HelperDate.getDateInMilliFromDate(year, month, day);
                } else
                    MyToaster.getToast(this, getString(R.string.age_restriction_warning));
            } catch (Exception e) {
                MyToaster.getUnknownErrorToast(this);
                e.printStackTrace();
            }
        };

        RadioGroup genderGroup = findViewById(R.id.radio_group);
        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_male:
                    gender = 0;
                    break;

                case R.id.radio_female:
                    gender = 1;
                    break;

                case R.id.radio_other:
                    gender = 2;
                    break;
            }
            genderChecked = true;
        });
    }

    private void setNickName(int nickType) {
        switch (nickType) {
            case 1:
                eNickName.setText(nickNameFirst);
                HelperUI.setBackgroundDefault(eNickName, eNickNameHead, getString(R.string.nick_name), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
                break;
            case 2:
                eNickName.setText(nickNameSecond);
                HelperUI.setBackgroundDefault(eNickName, eNickNameHead, getString(R.string.nick_name), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
                break;
        }
    }

    private void startSignUp() {
        onGetData();
        registerNickOffer.setVisibility(View.GONE);
        registerNickOfferOne.setVisibility(View.GONE);
        registerNickOfferTwo.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperMedia.REQUEST_PICK_IMAGE) {
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
        String picturePath = HelperMedia.getPathFromImageUri(this, uri);
        if (picturePath != null) {
            profilePhotoFile = new File(picturePath);
            signUpPresenter.uploadToS3(ClientManager.transferObserver(this, profilePhotoFile));
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

        loadingVisibility(false);

        switch (signUpResponse.getStatus()) {
            case 0:
                DialogManager.signUpConfirmDialog(this, signUpResponse.getTitle(), signUpResponse.getMessage());
//                showConfirmDialog(signUpResponse.getTitle(), signUpResponse.getMessage());
                break;

            case 2:
                HelperUI.setBackgroundWarning(eNickName, eNickNameHead, getString(R.string.nick_name), this);
                signUpPresenter.checkNick(new CheckNickRequest(nickName, userName, userSurname));
                break;

            case 3:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;

            case 4:
                MyToaster.getToast(this, signUpResponse.getMessage());
                HelperUI.setBackgroundWarning(eMail, eEmailHead, getString(R.string.email), this);
                break;

            case 5:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;

            case 6:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;

            case 7:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;

            case 8:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;

            case 9:
                MyToaster.getToast(this, signUpResponse.getMessage());
                break;
        }
    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
        MyToaster.getToast(this, message);
    }

    @Override
    public void onPhotoUploadToS3() {
        photoUrl = ClientManager.amazonS3Client(this).getResourceUrl(GlobalPreferences.getAppSettings(this).getS3_BUCKET_NAME(), profilePhotoFile.getName());
    }

    @Override
    public void onGetNickCheckResult(CheckNickResponse checkNickResponse) {
        try {
            registerNickOffer.setVisibility(View.VISIBLE);
            registerNickOfferOne.setVisibility(View.VISIBLE);
            registerNickOfferTwo.setVisibility(View.VISIBLE);
            nickNameFirst = checkNickResponse.getNicknames_to_offer().get(0);
            nickNameSecond = checkNickResponse.getNicknames_to_offer().get(1);
            registerNickOfferOne.setText(nickNameFirst);
            registerNickOfferTwo.setText(nickNameSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermissionAndStartPick() {
        if (isPermissionGranted(READ_EXTERNAL_STORAGE)) {
            HelperMedia.startImagePicker(this);
        } else {
            requestPermission(READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionResult(boolean permissionGranted) {
        if (permissionGranted) {
            HelperMedia.startImagePicker(this);
        } else {
            MyToaster.getToast(this, "No permission granted");
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

        if (!genderChecked) {
            MyToaster.getToast(this, getString(R.string.gender_restriction_warning));
            return;
        }

        userName = HelperUI.checkEditTextData(eName, eNameHead, getString(R.string.name), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        userSurname = HelperUI.checkEditTextData(eSurname, eSurnameHead, getString(R.string.surname), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        nickName = HelperUI.checkEditTextData(eNickName, eNickNameHead, getString(R.string.nick_name), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);

        String email = HelperUI.checkEditTextData(eMail, eEmailHead, getString(R.string.email), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);
        if (email != null && HelperUI.isEmailValid(email)) {
            HelperUI.setBackgroundDefault(eMail, eEmailHead, getString(R.string.email), blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eMail, eEmailHead, getString(R.string.email), this);
        }

        String password = HelperUI.checkEditTextData(ePassword, ePasswordHead, getString(R.string.password), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);
        if (password != null && HelperUI.checkPassword(password)) {
            HelperUI.setBackgroundDefault(ePassword, ePasswordHead, getString(R.string.password), blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(ePassword, ePasswordHead, getString(R.string.password), this);
        }

        String confirmPassword = HelperUI.checkEditTextData(eConfirmPassword, eConfirmPasswordHead, getString(R.string.confirm_password), blackColor, HelperUI.BACKGROUND_DEF_BLACK, this);
        if (password != null && confirmPassword != null && HelperUI.checkConfirmPassword(password, confirmPassword)) {
            HelperUI.setBackgroundDefault(eConfirmPassword, eConfirmPasswordHead, getString(R.string.confirm_password), blackColor, HelperUI.BACKGROUND_DEF_BLACK);
        } else {
            HelperUI.setBackgroundWarning(eConfirmPassword, eConfirmPasswordHead, getString(R.string.confirm_password), this);
        }

        if (ePhoneNumber.getText().toString().isEmpty() || !checkNumber(ePhoneNumber)) {
            phoneNumberContainer.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_fields_warning));
            ePhoneNumberHead.setText(String.format("* %s", getString(R.string.phone_number)));
            ePhoneNumberHead.setTextColor(ContextCompat.getColor(this, R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(phoneNumberContainer);
        } else {
            phoneNumberContainer.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_signup_fields));
            ePhoneNumberHead.setText(String.format("* %s", getString(R.string.phone_number)));
            ePhoneNumberHead.setTextColor(ContextCompat.getColor(this, R.color.black));
            phoneNumber = ePhoneNumber.getText().toString();

            String name = countryCodePicker.getSelectedCountryName();
            String code = countryCodePicker.getSelectedCountryCodeWithPlus();
            phoneIndex = countryCodePicker.getSelectedCountryCode();
            String asds = countryCodePicker.getSelectedCountryEnglishName();

            //registerPhoneNumber.getText().clear();
        }

        if (registerBirthDate.getText().toString().isEmpty()) {
            registerBirthDate.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_fields_warning));
            registerBirthDateHead.setText(String.format("* %s", getString(R.string.birth_date)));
            registerBirthDateHead.setTextColor(ContextCompat.getColor(this, R.color.red));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(registerBirthDate);
        } else {
            registerBirthDate.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_signup_fields));
            registerBirthDateHead.setText(String.format("* %s", getString(R.string.birth_date)));
            registerBirthDateHead.setTextColor(ContextCompat.getColor(this, R.color.black));
            birthDate = registerBirthDate.getText().toString();
        }

        //TODO: check photoUrl on null

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
                    confirmPassword, String.valueOf(timeStamp), phoneIndex, photoUrl,
                    phoneNumber, String.valueOf(GlobalPreferences.getLanguageId(this)), gender);
            signUpPresenter.signUp(signUpRequest);
        }

    }

    @Override
    protected void onDestroy() {
        if (signUpPresenter != null) {
            signUpPresenter = null;
        }
        super.onDestroy();
    }
}
