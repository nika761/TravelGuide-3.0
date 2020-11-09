package com.travel.guide.ui.home.profile.editProfile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.guide.R;
import com.travel.guide.enums.InputFieldPairs;
import com.travel.guide.helper.HelperDialogs;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.helper.HelperPref;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.enums.InputFieldPairs.BIO;
import static com.travel.guide.enums.InputFieldPairs.COUNTRY;
import static com.travel.guide.enums.InputFieldPairs.EMAIL;
import static com.travel.guide.enums.InputFieldPairs.NAME;
import static com.travel.guide.enums.InputFieldPairs.NICKNAME;
import static com.travel.guide.enums.InputFieldPairs.PASSWORD;
import static com.travel.guide.enums.InputFieldPairs.SURNAME;
import static com.travel.guide.helper.HelperPref.getAccessToken;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileEditActivity extends AppCompatActivity implements ProfileEditListener {

    private ProfileEditPresenter presenter;
    private File imageFile;

    private EditText name, surName, nickName, email, phoneNumber, country, password, bio;
    private TextView nameHead, surNameHead, nickNameHead, birthDateHead, emailHead, phoneNumberhead, countryHead, passwordHead, bioHead;
    private CircleImageView userImage;
    private TextView birthDate;
    private String imagePath;

    private final static int PICK_IMAGE = 28;

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

        password = findViewById(R.id.edit_password);
        passwordHead = findViewById(R.id.edit_password_head);

        bio = findViewById(R.id.edit_bio);
        bioHead = findViewById(R.id.edit_bio_head);

        birthDate = findViewById(R.id.edit_birth_date);
        birthDateHead = findViewById(R.id.edit_birth_date_head);

        phoneNumber = findViewById(R.id.edit_phone_number);
        phoneNumberhead = findViewById(R.id.edit_phone_number_head);

        TextView saveBtn = findViewById(R.id.edit_save_btn);
        saveBtn.setOnClickListener(v -> checkInputData());

        TextView toolbarBackBtn = findViewById(R.id.user_prf_back_btn);
        toolbarBackBtn.setOnClickListener(v -> finish());

        View changePhotoBtn = findViewById(R.id.change_photo_btn);
        changePhotoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
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
        String picturePath = HelperMedia.getPathFromImageUri(this, uri);
        imageFile = new File(picturePath);
//            signUpPresenter.uploadToS3(HelperClients.transferObserver(this, profilePhotoFile));
        HelperMedia.loadCirclePhoto(this, picturePath, userImage);

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    void checkInputData() {
        HashMap<TextView, EditText> namePair = new HashMap<>();
        namePair.put(nameHead, name);

        HashMap<TextView, EditText> surnamePair = new HashMap<>();
        surnamePair.put(surNameHead, surName);

        HashMap<TextView, EditText> nickNamePair = new HashMap<>();
        nickNamePair.put(nickNameHead, nickName);

        HashMap<TextView, EditText> emailPair = new HashMap<>();
        emailPair.put(emailHead, email);

        HashMap<TextView, EditText> countryPair = new HashMap<>();
        countryPair.put(countryHead, country);

        HashMap<TextView, EditText> passwordPair = new HashMap<>();
        passwordPair.put(passwordHead, password);

        HashMap<TextView, EditText> bioPair = new HashMap<>();
        bioPair.put(bioHead, bio);

        HashMap<InputFieldPairs, HashMap<TextView, EditText>> inputFields = new HashMap<>();

        inputFields.put(NAME, namePair);
        inputFields.put(SURNAME, surnamePair);
        inputFields.put(NICKNAME, nickNamePair);
        inputFields.put(EMAIL, emailPair);
        inputFields.put(COUNTRY, countryPair);
        inputFields.put(PASSWORD, passwordPair);
        inputFields.put(BIO, bioPair);

        HashMap<InputFieldPairs, String> checkedInputData = HelperUI.checkInputData(this, inputFields);

        if (checkedInputData.size() != inputFields.size()) {
            Log.e("dasdasdas", "data null");
        } else {
            String name = checkedInputData.get(NAME);
            String surname = checkedInputData.get(SURNAME);
            String nickname = checkedInputData.get(NICKNAME);
            String email = checkedInputData.get(EMAIL);
            String country = checkedInputData.get(COUNTRY);
            String password = checkedInputData.get(PASSWORD);
            String bio = checkedInputData.get(BIO);

            HelperDialogs.profileInfoUpdatedDialog(this);

            Log.e("dasdasdas", "სახელი" + " " + name);
            Log.e("dasdasdas", "გვარი" + " " + surname);
            Log.e("dasdasdas", "ნიკი" + " " + nickname);
            Log.e("dasdasdas", "ემაილი" + " " + email);
            Log.e("dasdasdas", "ქვეყანა" + " " + country);
            Log.e("dasdasdas", "პაროლი" + " " + password);
            Log.e("dasdasdas", "ბიო" + " " + bio);
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

//        TO_DO
//        password.setText(userinfo.getpassword());

        birthDate.setText(userInfo.getDate_of_birth());

    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
