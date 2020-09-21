package com.example.travelguide.ui.home.profile.editProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;
import com.example.travelguide.ui.home.HomePageActivity;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.LoginResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private EditText name,
            surName, nickName, email, phoneNumber, country, password, bio;
    private CircleImageView userImage;
    private TextView toolbarBackBtn, birthDate;
    private Context context;
    private View changePhotoBtn;

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

        changePhotoBtn = view.findViewById(R.id.change_photo_btn);
        changePhotoBtn.setOnClickListener(this::onViewClick);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getContext() != null)
            ((HomePageActivity) getContext()).hideBottomNavigation(false);
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
        if (HelperPref.getAccessToken(context) != null) {
//
//            List<LoginResponse.User> loggedUsers = HelperPref.getServerSavedUsers(context);
//            name.setText(loggedUsers.get(0).getName());
//            surName.setText(loggedUsers.get(0).getLastname());
//            nickName.setText(loggedUsers.get(0).getNickname());
//            email.setText(loggedUsers.get(0).getEmail());
//            birthDate.setText(loggedUsers.get(0).getDate_of_birth());
//            phoneNumber.setText(loggedUsers.get(0).getPhone_num());

        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }

    }

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.user_prf_back_btn:
                ((HomePageActivity) context).onBackPressed();
                break;

            case R.id.change_photo_btn:
                Intent intent = new Intent(context, ChangePhotoActivity.class);
                context.startActivity(intent);
                break;

        }
    }

    @Override
    public void onDestroy() {
        if (getContext() != null)
            ((HomePageActivity) getContext()).hideBottomNavigation(true);
        super.onDestroy();
    }

}
