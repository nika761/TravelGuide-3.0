package com.example.travelguide.ui.customerUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.ui.customerUser.followerActivity.CustomerFollowerActivity;
import com.example.travelguide.ui.customerUser.post.CustomerPhotoFragment;
import com.example.travelguide.ui.customerUser.tour.CustomerTourFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CustomerProfileActivity extends AppCompatActivity implements CustomerProfileListener, View.OnClickListener {

    private CustomerProfilePresenter customerProfilePresenter;
    private TextView userName;
    private TextView nickName;
    private TextView following;
    private TextView follower;
    private TextView reaction;
    private TextView bio;
    private TextView followBtn;
    private FrameLayout loaderContainer;
    private LottieAnimationView lottieLoader;
    private CircleImageView image;
    private int customerUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        this.customerUserId = getIntent().getIntExtra("id", 0);
        initUI();

        if (customerUserId != 0) {
            customerProfilePresenter.getProfile(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new ProfileRequest(customerUserId));
        }
    }

    private void initUI() {

        customerProfilePresenter = new CustomerProfilePresenter(this);

        ViewPager viewPager = findViewById(R.id.customer_view_pager);
        TabLayout tabLayout = findViewById(R.id.customer_profile_tab);

        CustomerPagerAdapter customerPagerAdapter = new CustomerPagerAdapter(getSupportFragmentManager());

        if (customerUserId != 0)
            customerPagerAdapter.addFragment(new CustomerPhotoFragment(customerUserId));

        customerPagerAdapter.addFragment(new CustomerTourFragment());
        viewPager.setAdapter(customerPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.profile_photos);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.profile_tour);

        ImageButton backBtn = findViewById(R.id.customer_profile_back_btn);
        backBtn.setOnClickListener(this);

        TextView report = findViewById(R.id.customer_profile_report);
        report.setOnClickListener(this);

        followBtn = findViewById(R.id.customer_profile_follow_btn);
        followBtn.setOnClickListener(this);

        ConstraintLayout followersContainer = findViewById(R.id.customer_profile_follow_container);
        followersContainer.setOnClickListener(this);

        userName = findViewById(R.id.customer_profile_name);
        nickName = findViewById(R.id.customer_profile_nickName);
        following = findViewById(R.id.customer_profile_following_count);
        follower = findViewById(R.id.customer_profile_followers_count);
        reaction = findViewById(R.id.customer_profile_reaction_count);
        image = findViewById(R.id.customer_profile_image);
        bio = findViewById(R.id.customer_bio_text);

        loaderContainer = findViewById(R.id.customer_profile_frame);
        lottieLoader = findViewById(R.id.loader_customer_profile);
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {

        ProfileResponse.Userinfo userInfo = profileResponse.getUserinfo();
        userName.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        nickName.setText(String.format("@%s", userInfo.getNickname()));
        following.setText(String.valueOf(userInfo.getFollowing()));
        follower.setText(String.valueOf(userInfo.getFollower()));
        reaction.setText(String.valueOf(userInfo.getReactions()));
        bio.setText(userInfo.getBiography());

        if (userInfo.getProfile_pic() != null)
            HelperMedia.loadCirclePhoto(this, userInfo.getProfile_pic(), image);

        if (userInfo.getFollow() == 1)
            followBtn.setText("Following");

        loaderContainer.setVisibility(View.GONE);
        lottieLoader.setVisibility(View.GONE);
    }

    @Override
    public void onGerProfileError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {
        switch (followResponse.getStatus()) {
            case 0:
                followBtn.setText("Following");
                Toast.makeText(this, followResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;

            case 1:
                followBtn.setText("Follow");
                Toast.makeText(this, followResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onFollowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_profile_back_btn:
                finish();
                break;


            case R.id.customer_profile_report:
                break;

            case R.id.customer_profile_follow_btn:
                customerProfilePresenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new FollowRequest(customerUserId));
                break;

            case R.id.customer_profile_follow_container:
                Intent intent = new Intent(this, CustomerFollowerActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (customerProfilePresenter != null) {
            customerProfilePresenter = null;
        }
        super.onDestroy();
    }
}

