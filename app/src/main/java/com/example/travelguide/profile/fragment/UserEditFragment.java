package com.example.travelguide.profile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;
import com.example.travelguide.home.activity.UserPageActivity;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.LoginResponseModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserEditFragment extends Fragment {

    private EditText name,
            surName, nickName, email, phoneNumber, country, password, bio;
    private CircleImageView userImage;
    private TextView toolbarBackBtn, birthDate;
    private Context context;
    private ImageView changePhotoImage, uploadPhotoBtn;
    private View changePhotoLayout;
    private Button changePhotoCancelBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        name = view.findViewById(R.id.edit_name);
        surName = view.findViewById(R.id.edit_surname);
        nickName = view.findViewById(R.id.edit_nick_name);
        email = view.findViewById(R.id.edit_email);
        country = view.findViewById(R.id.edit_country);
        password = view.findViewById(R.id.edit_password);
        bio = view.findViewById(R.id.edit_bio);
        userImage = view.findViewById(R.id.edit_profile_image);
        birthDate = view.findViewById(R.id.edit_birth_date);
        phoneNumber = view.findViewById(R.id.edit_phone_number);

        toolbarBackBtn = view.findViewById(R.id.user_prf_back_btn);
        toolbarBackBtn.setOnClickListener(this::onViewClick);

        changePhotoLayout = view.findViewById(R.id.change_photo_layout);
        changePhotoImage = view.findViewById(R.id.change_photo_image);

        uploadPhotoBtn = view.findViewById(R.id.change_photo_btn);
        uploadPhotoBtn.setOnClickListener(this::onViewClick);

        changePhotoCancelBtn = view.findViewById(R.id.change_edit_profile_photo_cancel_btn);
        changePhotoCancelBtn.setOnClickListener(this::onViewClick);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getContext() != null)
            ((UserPageActivity) getContext()).hideBottomNavigation(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserData();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setUserData() {
        if (HelperPref.getCurrentAccessToken(context) != null) {

            List<LoginResponseModel.User> loggedUsers = HelperPref.getServerSavedUsers(context);
            name.setText(loggedUsers.get(0).getName());
            surName.setText(loggedUsers.get(0).getLastname());
            nickName.setText(loggedUsers.get(0).getNickname());
            email.setText(loggedUsers.get(0).getEmail());
            birthDate.setText(loggedUsers.get(0).getDate_of_birth());
            phoneNumber.setText(loggedUsers.get(0).getPhone_num());

        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }

    }

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.user_prf_back_btn:
                ((UserPageActivity) context).onBackPressed();
                break;

            case R.id.change_photo_btn:
                changePhotoLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.change_edit_profile_photo_cancel_btn:
                changePhotoLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (getContext() != null)
            ((UserPageActivity) getContext()).hideBottomNavigation(true);
        super.onDestroy();
    }

}
