package com.travel.guide.ui.home.profile.editProfile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.helper.HelperPref;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.helper.HelperPref.getAccessToken;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileEditActivity extends AppCompatActivity implements ProfileEditListener {

    private ProfileEditPresenter profileEditPresenter;
    private File imageFile;

    private EditText name, surName, nickName, email, phoneNumber, country, password, bio;
    private CircleImageView userImage;
    private TextView birthDate;
    private String imagePath;

    private final static int PICK_IMAGE = 28;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initUI();
        profileEditPresenter.getProfile(ACCESS_TOKEN_BEARER + getAccessToken(this), new ProfileRequest(HelperPref.getUserId(this)));
    }

    private void initUI() {

        profileEditPresenter = new ProfileEditPresenter(this);

        name = findViewById(R.id.edit_name);
        surName = findViewById(R.id.edit_surname);
        nickName = findViewById(R.id.edit_nick_name);
        email = findViewById(R.id.edit_email);
        country = findViewById(R.id.edit_country);
        password = findViewById(R.id.edit_password);
        bio = findViewById(R.id.edit_bio);
        userImage = findViewById(R.id.edit_profile_image);
        birthDate = findViewById(R.id.edit_birth_date);
        phoneNumber = findViewById(R.id.edit_phone_number);

        TextView toolbarBackBtn = findViewById(R.id.user_prf_back_btn);
        toolbarBackBtn.setOnClickListener(this::onViewClick);

        View changePhotoBtn = findViewById(R.id.change_photo_btn);
        changePhotoBtn.setOnClickListener(this::onViewClick);

    }

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.user_prf_back_btn:
                finish();
                break;

            case R.id.change_photo_btn:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
                break;
        }
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
        if (profileEditPresenter != null) {
            profileEditPresenter = null;
        }
//            ((HomePageActivity) getContext()).hideBottomNavigation(true);
        super.onDestroy();
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
