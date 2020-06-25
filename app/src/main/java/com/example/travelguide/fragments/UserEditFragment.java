package com.example.travelguide.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.UtilsGlide;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserEditFragment extends Fragment {

    private EditText nameField,
            surnameField, nickaNameField, birthDateField, emailField, phoneNumberField, countryField, passwordField, bioField;
    private CircleImageView userImage;
    private TextView toolbarBackBtn;
    private Context context;
    private ImageView changePhotoImage, uploadPhotoBtn;
    private View changePhotoLayout;
    private Button changePhotoCancelBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

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
        iniUI(view);
        onGetData();
        setClickListeners();
    }

    @Override
    public void onDestroy() {
        if (getContext() != null)
            ((UserPageActivity) getContext()).hideBottomNavigation(true);
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void iniUI(View v) {
        nameField = v.findViewById(R.id.edit_name);
        surnameField = v.findViewById(R.id.edit_surname);
        nickaNameField = v.findViewById(R.id.edit_nick_name);
        emailField = v.findViewById(R.id.edit_email);
        countryField = v.findViewById(R.id.edit_country);
        passwordField = v.findViewById(R.id.edit_password);
        bioField = v.findViewById(R.id.edit_bio);
        userImage = v.findViewById(R.id.edit_profile_image);
        toolbarBackBtn = v.findViewById(R.id.user_prf_back_btn);
        changePhotoLayout = v.findViewById(R.id.change_photo_layout);
        changePhotoImage = v.findViewById(R.id.change_photo_image);
        uploadPhotoBtn = v.findViewById(R.id.change_photo_btn);
        changePhotoCancelBtn = v.findViewById(R.id.change_edit_profile_photo_cancel_btn);
    }

    private void onGetData() {
        if (getArguments() != null && getArguments().containsKey("user")) {
            User user = (User) getArguments().getSerializable("user");
            if (user != null)
                setUserData(user);
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserData(User currentUser) {
        String firstName = currentUser.getName();
        String lastName = currentUser.getLastName();
        String url = currentUser.getUrl();
        String id = currentUser.getId();
        String email = currentUser.getEmail();
        String loginType = currentUser.getLoginType();

        if (url != null) {
            UtilsGlide.loadPhoto(context, url, userImage);
        }
        nameField.setText(firstName);
        surnameField.setText(lastName);
        emailField.setText(email);
        nameField.setText(firstName);

    }

    private void setClickListeners() {
        toolbarBackBtn.setOnClickListener(this::onViewClick);
        uploadPhotoBtn.setOnClickListener(this::onViewClick);
        changePhotoCancelBtn.setOnClickListener(this::onViewClick);
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
}
