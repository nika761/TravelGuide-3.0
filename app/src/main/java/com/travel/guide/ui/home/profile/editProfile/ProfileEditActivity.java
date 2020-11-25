package com.travel.guide.ui.home.profile.editProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;
import com.travel.guide.R;
import com.travel.guide.enums.InputFieldPairs;
import com.travel.guide.helper.HelperClients;
import com.travel.guide.helper.HelperDialogs;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.request.UpdateProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.response.UpdateProfileResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.enums.InputFieldPairs.BIO;
import static com.travel.guide.enums.InputFieldPairs.BIRTHDATE;
import static com.travel.guide.enums.InputFieldPairs.COUNTRY;
import static com.travel.guide.enums.InputFieldPairs.EMAIL;
import static com.travel.guide.enums.InputFieldPairs.NAME;
import static com.travel.guide.enums.InputFieldPairs.NICKNAME;
import static com.travel.guide.enums.InputFieldPairs.PASSWORD;
import static com.travel.guide.enums.InputFieldPairs.SURNAME;
import static com.travel.guide.helper.HelperPref.getAccessToken;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileEditActivity extends AppCompatActivity implements ProfileEditListener {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CountryCodePicker countryCodePicker;
    private ProfileEditPresenter presenter;

    private File imageFile;

    private EditText name, surName, nickName, email, phoneNumber, birthDate, country, password, bio;
    private TextView nameHead, surNameHead, nickNameHead, birthDateHead, emailHead, phoneNumberhead, countryHead, passwordHead, bioHead;
    private CircleImageView userImage;
    private String photoUrl;

    private final static int PICK_IMAGE = 28;
    private boolean genderChecked;
    private long timeStamp;
    private int gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initUI();
        presenter.getProfile(ACCESS_TOKEN_BEARER + getAccessToken(this), new ProfileRequest(HelperPref.getUserId(this)));
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

        country = findViewById(R.id.edit_country);
        countryHead = findViewById(R.id.edit_country_head);

        bio = findViewById(R.id.edit_bio);
        bioHead = findViewById(R.id.edit_bio_head);

        birthDate = findViewById(R.id.edit_birth_date);
        birthDate.setOnClickListener(v -> HelperDialogs.datePickerDialog(this, mDateSetListener));

        birthDateHead = findViewById(R.id.edit_birth_date_head);

        phoneNumber = findViewById(R.id.edit_phone_number);
        phoneNumberhead = findViewById(R.id.edit_phone_number_head);

        countryCodePicker = findViewById(R.id.number_picker);

        TextView saveBtn = findViewById(R.id.edit_save_btn);
        saveBtn.setOnClickListener(v -> checkInputData());

        TextView toolbarBackBtn = findViewById(R.id.user_prf_back_btn);
        toolbarBackBtn.setOnClickListener(v -> finish());

        View changePhotoBtn = findViewById(R.id.change_photo_btn);
        changePhotoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });


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
                birthDate.setText(date);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                timeStamp = calendar.getTimeInMillis();
                Log.e("datetime", String.valueOf(timeStamp));
            }
        };

        RadioGroup genderGroup = findViewById(R.id.edit_radio_group);
        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.edit_radio_male:
                    gender = 0;
                    break;

                case R.id.edit_radio_female:
                    gender = 1;
                    break;

                case R.id.edit_radio_other:
                    gender = 2;
                    break;
            }
            genderChecked = true;
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null)
                        pickProfileImage(uri);
                }
            }
        }
    }

    public void pickProfileImage(Uri uri) {
        try {
            String picturePath = HelperMedia.getPathFromImageUri(this, uri);
            imageFile = new File(picturePath);
            presenter.uploadToS3(HelperClients.transferObserver(this, imageFile));
            HelperMedia.loadCirclePhoto(this, picturePath, userImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    void checkInputData() {

        if (!genderChecked) {
            Toast.makeText(this, "Please enter gender", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<TextView, EditText> namePair = new HashMap<>();
        namePair.put(nameHead, name);

        HashMap<TextView, EditText> surnamePair = new HashMap<>();
        surnamePair.put(surNameHead, surName);

        HashMap<TextView, EditText> nickNamePair = new HashMap<>();
        nickNamePair.put(nickNameHead, nickName);

        HashMap<TextView, EditText> emailPair = new HashMap<>();
        emailPair.put(emailHead, email);

        HashMap<TextView, EditText> birthDatePair = new HashMap<>();
        birthDatePair.put(birthDateHead, birthDate);

        HashMap<TextView, EditText> countryPair = new HashMap<>();
        countryPair.put(countryHead, country);

        HashMap<TextView, EditText> bioPair = new HashMap<>();
        bioPair.put(bioHead, bio);

        HashMap<InputFieldPairs, HashMap<TextView, EditText>> inputFields = new HashMap<>();

        inputFields.put(NAME, namePair);
        inputFields.put(SURNAME, surnamePair);
        inputFields.put(NICKNAME, nickNamePair);
        inputFields.put(EMAIL, emailPair);
        inputFields.put(BIRTHDATE, birthDatePair);
        inputFields.put(COUNTRY, countryPair);
        inputFields.put(BIO, bioPair);

        HashMap<InputFieldPairs, String> checkedInputData = HelperUI.checkInputData(this, inputFields);

        if (checkedInputData.size() != inputFields.size()) {
            Log.e("dasdasdas", "data null");
        } else {

//            presenter.updateProfile(HelperPref.getAccessToken(this), updateProfileRequest);
//            Log.e("dasdasdas", "სახელი" + " " + name);
//            Log.e("dasdasdas", "გვარი" + " " + surname);
//            Log.e("dasdasdas", "ნიკი" + " " + nickname);
//            Log.e("dasdasdas", "ემაილი" + " " + email);
//            Log.e("dasdasdas", "ქვეყანა" + " " + country);
//            Log.e("dasdasdas", "პაროლი" + " " + password);
//            Log.e("dasdasdas", "ბიო" + " " + bio);
        }
    }


    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {
        HelperMedia.loadCirclePhoto(this, userInfo.getProfile_pic(), userImage);
        name.setText(userInfo.getName());
        surName.setText(userInfo.getLastname());
        nickName.setText(userInfo.getNickname());
        email.setText(userInfo.getEmail());
        phoneNumber.setText(userInfo.getPhone_number());
        country.setText(userInfo.getCity());
        bio.setText(userInfo.getBiography());
        birthDate.setText(userInfo.getDate_of_birth());

    }

    @Override
    public void onUpdateProfile(UpdateProfileResponse updateProfileResponse) {
        switch (updateProfileResponse.getStatus()) {
            case 0:
                HelperDialogs.profileInfoUpdatedDialog(this);
                break;
        }
    }

    @Override
    public void onPhotoUploadedToS3() {
        photoUrl = HelperClients.amazonS3Client(this).getResourceUrl(HelperClients.S3_BUCKET, imageFile.getName());
    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
