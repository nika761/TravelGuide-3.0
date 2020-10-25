package com.example.travelguide.ui.home.profile.editProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.HelperSystem;
import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.ui.home.HomePageActivity;
import com.example.travelguide.helper.HelperPref;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.helper.HelperPref.getAccessToken;
import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

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
        profileEditPresenter = new ProfileEditPresenter(this);
        profileEditPresenter.getProfile(ACCESS_TOKEN_BEARER + getAccessToken(this), new ProfileRequest(HelperPref.getCurrentUserId(this)));
    }

    private void initUI() {

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

//            ((HomePageActivity) getContext()).hideBottomNavigation(false);

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.user_prf_back_btn:
                finish();
                break;

            case R.id.change_photo_btn:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
                    if (uri != null && uri.getPath() != null)
                        imageFile = new File(uri.getPath());
                }
            }
        }
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

//        TO_DO
//        password.setText(userinfo.getpassword());

        bio.setText(userInfo.getBiography());
        birthDate.setText(userInfo.getDate_of_birth());
    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
