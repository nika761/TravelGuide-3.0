package travelguideapp.ge.travelguide.ui.profile.editProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.hbb20.CountryCodePicker;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.HelperDate;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.model.customModel.AppSettings;
import travelguideapp.ge.travelguide.model.customModel.Country;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.request.UpdateProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.login.password.ChangePasswordFragment;
import travelguideapp.ge.travelguide.ui.profile.changeCountry.CountriesDialogFragment;
import travelguideapp.ge.travelguide.ui.profile.changeCountry.ChooseCountryListener;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.model.response.UpdateProfileResponse;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends BaseActivity implements ProfileEditListener, ChooseCountryListener {

    private CountryCodePicker countryCodePicker;
    private ProfileEditPresenter presenter;

    private File profileImageFile;

    private EditText name, surName, nickName, email, phoneNumber, city, password, bio;
    private TextView nameHead, surNameHead, nickNameHead, birthDate, country, birthDateHead, emailHead, phoneNumberHead, nickNameBusy, nickNameFirst, nickNameTwo, countryHead, cityHead, passwordHead, bioHead;
    private CircleImageView userImage;
    private RadioGroup genderGroup;
    private String photoUrl, nickFirst, nickSecond;
    private ConstraintLayout phoneCodeContainer;
    private ScrollView scrollView;

    private boolean genderChecked;
    private boolean dateChanged;
    private long timeStamp;
    private int gender, countryId;
    private long birthDateTimeStamp;

    private ProfileResponse.Userinfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getInfoFromExtra();
        initUI();
        getProfileInfo();
    }

    private void getInfoFromExtra() {
        try {
            this.userInfo = getIntent().getParcelableExtra("user_info");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProfileInfo() {
        if (userInfo == null) {
            presenter.getProfile(new ProfileRequest(GlobalPreferences.getUserId()));
        } else {
            setProfileInfo(userInfo);
        }
    }

    private void initUI() {

        presenter = new ProfileEditPresenter(this);

        userImage = findViewById(R.id.edit_profile_image);
        name = findViewById(R.id.edit_name);
        nameHead = findViewById(R.id.edit_name_head);
        surName = findViewById(R.id.edit_surname);
        surNameHead = findViewById(R.id.edit_surname_head);
        nickName = findViewById(R.id.edit_nick_name);
        nickNameHead = findViewById(R.id.edit_nick_name_head);
        email = findViewById(R.id.edit_email);
        emailHead = findViewById(R.id.edit_mail_head);
        scrollView = findViewById(R.id.linear_scroll_edit);

        country = findViewById(R.id.edit_country);
        country.setOnClickListener(v -> getCountryChooserDialog());

        countryHead = findViewById(R.id.edit_country_head);
        city = findViewById(R.id.edit_city);
        cityHead = findViewById(R.id.edit_city_head);
        bio = findViewById(R.id.edit_bio);
        bioHead = findViewById(R.id.edit_bio_head);
        birthDateHead = findViewById(R.id.edit_birth_date_head);
        genderGroup = findViewById(R.id.edit_radio_group);
        phoneNumber = findViewById(R.id.edit_phone_number);
        phoneNumberHead = findViewById(R.id.edit_phone_number_head);
        nickNameBusy = findViewById(R.id.edit_nickName_offer);

        nickNameFirst = findViewById(R.id.edit_nickName_offer_1);
        nickNameFirst.setOnClickListener(v -> onChooseNick(1));

        nickNameTwo = findViewById(R.id.edit_nickName_offer_2);
        nickNameTwo.setOnClickListener(v -> onChooseNick(2));

        countryCodePicker = findViewById(R.id.ccp);
        phoneCodeContainer = findViewById(R.id.edit_profile_phone_number_container);
        phoneCodeContainer.setOnClickListener(v -> countryCodePicker.setVisibility(View.VISIBLE));
        phoneNumber.setOnClickListener(v -> countryCodePicker.setVisibility(View.VISIBLE));
        phoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.hasFocus() && hasFocus) {
                countryCodePicker.setVisibility(View.VISIBLE);
            } else {
                countryCodePicker.setVisibility(View.GONE);
            }
        });

        birthDate = findViewById(R.id.edit_birth_date);
        birthDate.setOnClickListener(v -> DialogManager.datePickerDialog(this, getOnDateSetListener(), birthDateTimeStamp));

        TextView changePassword = findViewById(R.id.edit_change_password);
        changePassword.setOnClickListener(v -> {
            closeKeyBoard(v);
            loadChangePasswordFragment();
        });

        TextView saveBtn = findViewById(R.id.edit_save_btn);
        saveBtn.setOnClickListener(v -> {
            closeKeyBoard(v);
            onSaveAction();
        });

        TextView toolbarBackBtn = findViewById(R.id.user_prf_back_btn);
        toolbarBackBtn.setOnClickListener(v -> onBackPressed());

        View changePhotoBtn = findViewById(R.id.change_photo_btn);
        changePhotoBtn.setOnClickListener(v -> checkPermissionAndStartPick());

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
            MyToaster.showToast(this, "No permission granted");
        }
    }

    public void setScrollView(boolean visible) {
        try {
            if (visible) {
                scrollView.setVisibility(View.VISIBLE);
            } else {
                scrollView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void onPickImageFinish(Intent data) {
        if (data != null) {
            if (data.getData() != null) {
                profileImageFile = getPickedImage(data.getData());
            } else {
                MyToaster.showUnknownErrorToast(this);
            }
        } else {
            MyToaster.showUnknownErrorToast(this);
        }
    }

    private File getPickedImage(Uri uri) {
        String picturePath = HelperMedia.getPathFromImageUri(this, uri);
        HelperMedia.loadCirclePhoto(this, picturePath, userImage);
        photoUrl = null;
        return new File(picturePath);
    }

    private void onChooseNick(int wich) {
        switch (wich) {
            case 1:
                nickName.setText(nickFirst);
                nickNameBusy.setVisibility(View.GONE);
                HelperUI.inputDefault(this, nickName, nickNameHead, false);
                break;

            case 2:
                nickName.setText(nickSecond);
                nickNameBusy.setVisibility(View.GONE);
                HelperUI.inputDefault(this, nickName, nickNameHead, false);
                break;
        }

    }

    private void getCountryChooserDialog() {
        try {
            CountriesDialogFragment countryFragment = CountriesDialogFragment.getInstance(userInfo.getCountries(), this);

            if (countryFragment.getDialog() != null) {
                Dialog dialog = countryFragment.getDialog();
                if (dialog.getWindow() != null) {
                    if (dialog.getWindow().getAttributes() != null) {
                        dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                    }
                }
            }

            countryFragment.show(getSupportFragmentManager(), CountriesDialogFragment.TAG);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadChangePasswordFragment() {
        HelperUI.loadFragment(ChangePasswordFragment.getInstance(), null, R.id.change_password_fragment_container, true, false, this);
    }

    private DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        return (view, year, month, dayOfMonth) -> {
            try {
                int monthRight = month + 1;
                if (HelperDate.checkAgeOfUser(year, monthRight, dayOfMonth)) {
                    birthDate.setText(HelperDate.getDateStringFormat(year, month, dayOfMonth));
                    birthDateTimeStamp = HelperDate.getDateInMilliFromDate(year, month, dayOfMonth);
                    dateChanged = true;
                } else
                    MyToaster.showToast(ProfileEditActivity.this, getString(R.string.age_restriction_warning));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperMedia.REQUEST_PICK_IMAGE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    onPickImageFinish(data);
                    break;
                case Activity.RESULT_CANCELED:
//                    MyToaster.getUnknownErrorToast(this);
                    break;
            }
        }
    }


    private void onSaveAction() {
        try {
            getLoader(true);
            if (profileImageFile != null) {
                if (photoUrl == null)
                    presenter.uploadToS3(ClientManager.transferObserver(this, profileImageFile));
            } else {
                startUpdateInfo();
            }
        } catch (Exception e) {
            getLoader(false);
            e.printStackTrace();
        }

    }

    private void startUpdateInfo() {

        try {
            getLoader(true);

            nickNameBusy.setVisibility(View.GONE);
            nickNameFirst.setVisibility(View.GONE);
            nickNameTwo.setVisibility(View.GONE);

            String modelName = null;
            String modelSurname = null;
            String modelNick = null;
            String modelEmail = null;
            String modelNumber = null;
            String modelPhoneIndex = null;
            String modelBirthDate = null;
            String modelGender = null;

            if (isNullOrEmpty(name.getText().toString())) {
                HelperUI.inputWarning(this, name, nameHead, false);
            } else {
                HelperUI.inputDefault(this, name, nameHead, false);
                modelName = name.getText().toString();
            }

            if (isNullOrEmpty(surName.getText().toString())) {
                HelperUI.inputWarning(this, surName, surNameHead, false);
            } else {
                HelperUI.inputDefault(this, surName, surNameHead, false);
                modelSurname = surName.getText().toString();
            }

            if (isNullOrEmpty(nickName.getText().toString())) {
                HelperUI.inputWarning(this, nickName, nickNameHead, false);
            } else {
                HelperUI.inputDefault(this, nickName, nickNameHead, false);
                modelNick = nickName.getText().toString();
            }

            if (isNullOrEmpty(birthDate.getText().toString())) {
                HelperUI.inputWarning(this, birthDate, birthDateHead, false);
            } else {
                HelperUI.inputDefault(this, birthDate, birthDateHead, false);
                modelBirthDate = birthDate.getText().toString();
            }

            if (isNullOrEmpty(email.getText().toString())) {
                HelperUI.inputWarning(this, email, emailHead, false);
            } else {
                if (HelperUI.isEmailValid(email.getText().toString())) {
                    HelperUI.inputDefault(this, email, emailHead, false);
                    modelEmail = email.getText().toString();
                } else
                    HelperUI.inputWarning(this, email, emailHead, false);
            }

            if (!isNullOrEmpty(phoneNumber.getText().toString())) {
                if (checkNumber(phoneNumber)) {
                    HelperUI.inputDefault(this, phoneCodeContainer, phoneNumberHead, false);
                    modelNumber = phoneNumber.getText().toString();
                    modelPhoneIndex = countryCodePicker.getSelectedCountryCode();
                } else {
                    HelperUI.inputWarning(this, phoneCodeContainer, phoneNumberHead, false);
                }
            }

            if (!genderChecked) {
                MyToaster.showToast(this, getString(R.string.gender_restriction_warning));
            } else {
                modelGender = String.valueOf(gender);
            }

            if (modelName != null && modelSurname != null && modelNick != null && modelEmail != null && modelBirthDate != null && modelGender != null) {
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                updateProfileRequest.setName(modelName);
                updateProfileRequest.setLastname(modelSurname);
                updateProfileRequest.setNickname(modelNick);
                updateProfileRequest.setEmail(modelEmail);
                updateProfileRequest.setPhone_index(modelPhoneIndex);
                updateProfileRequest.setPhone_num(modelNumber);
                updateProfileRequest.setCity(city.getText().toString());
                updateProfileRequest.setBiography(bio.getText().toString());
                updateProfileRequest.setGender(modelGender);

                if (photoUrl != null) {
                    updateProfileRequest.setProfile_pic(photoUrl);
                }

                if (countryId != 0) {
                    updateProfileRequest.setCountry_id(countryId);
                }

                if (dateChanged) {
                    updateProfileRequest.setDate_of_birth(String.valueOf(birthDateTimeStamp));
                } else {
                    updateProfileRequest.setDate_of_birth(null);
                }

                presenter.updateProfile(updateProfileRequest);

            } else {
                getLoader(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLoader(false);
        }

    }


    @Override
    public void onGetProfileInfo(ProfileResponse.Userinfo userInfo) {
        if (userInfo != null)
            setProfileInfo(userInfo);
    }

    private void setProfileInfo(ProfileResponse.Userinfo userInfo) {
        try {
            this.userInfo = userInfo;
            HelperMedia.loadCirclePhoto(this, userInfo.getProfile_pic(), userImage);
            name.setText(userInfo.getName());
            surName.setText(userInfo.getLastname());
            nickName.setText(userInfo.getNickname());
            email.setText(userInfo.getEmail());
            phoneNumber.setText(userInfo.getPhone_number());
            country.setText(userInfo.getCountry());
            city.setText(userInfo.getCity());
            bio.setText(userInfo.getBiography());
            birthDate.setText(userInfo.getDate_of_birth());
            birthDateTimeStamp = HelperDate.getTimeInMillisFromServerDateString(userInfo.getDate_of_birth());
            Log.e("aczxczxczxczxc", String.valueOf(birthDateTimeStamp));
            setGender(userInfo.getGender());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGender(int gender) {
        switch (gender) {
            case 0:
                genderGroup.check(R.id.edit_radio_male);
                this.gender = 0;
                genderChecked = true;
                break;
            case 1:
                this.gender = 1;
                genderChecked = true;
                genderGroup.check(R.id.edit_radio_female);
                break;
            case 2:
                genderGroup.check(R.id.edit_radio_other);
                this.gender = 2;
                genderChecked = true;
                break;
        }

        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.edit_radio_male:
                    this.gender = 0;
                    genderChecked = true;
                    break;

                case R.id.edit_radio_female:
                    this.gender = 1;
                    genderChecked = true;
                    break;

                case R.id.edit_radio_other:
                    this.gender = 2;
                    genderChecked = true;
                    break;
            }
        });

    }

    private void finishUpdateFlow(String title, String body) {
        try {
            AlertDialog dialog = DialogManager.profileUpdatedDialog(this, title, body);
            dialog.show();
            new Handler().postDelayed(() -> {
                dialog.dismiss();
                onBackPressed();
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateSuccess(UpdateProfileResponse updateProfileResponse) {
        getLoader(false);
        switch (updateProfileResponse.getStatus()) {
            case 0:
                finishUpdateFlow(updateProfileResponse.getTitle(), updateProfileResponse.getBody());
                break;

            case 2:
                onNickNameBusy(updateProfileResponse.getNicknames_to_offer(), updateProfileResponse.getMessage());
                break;

            default:
                MyToaster.showToast(this, updateProfileResponse.getMessage());
        }
    }

    public void onNickNameBusy(List<String> nicks, String message) {
        try {
            HelperUI.inputWarning(this, nickName, nickNameHead, false);
            nickNameBusy.setVisibility(View.VISIBLE);
            nickNameFirst.setVisibility(View.VISIBLE);
            nickNameTwo.setVisibility(View.VISIBLE);
            nickNameBusy.setText(message);
            nickFirst = nicks.get(0);
            nickSecond = nicks.get(1);
            nickNameFirst.setText(nickFirst);
            nickNameTwo.setText(nickSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPhotoUploadedToS3() {
        photoUrl = ClientManager.amazonS3Client(this).getResourceUrl(AppSettings.create(GlobalPreferences.getAppSettings()).getS3_BUCKET_NAME(), profileImageFile.getName());
        startUpdateInfo();
    }

    private void getLoader(boolean show) {
        FrameLayout frameLayout = findViewById(R.id.edit_loader_container);
        LottieAnimationView lottieAnimationView = findViewById(R.id.edit_loader);
        if (show) {
            frameLayout.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String message) {
        getLoader(false);
        MyToaster.showToast(this, message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_activity_slide_in_left, R.anim.anim_activity_slide_out_rigth);
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onChoseCountry(Country country) {
        this.country.setText(country.getName());
        this.countryId = country.getId();
    }

}
