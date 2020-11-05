package com.travel.guide.ui.customerUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.ui.customerUser.post.CustomerPhotoFragment;
import com.travel.guide.ui.customerUser.tour.CustomerTourFragment;
import com.google.android.material.tabs.TabLayout;
import com.travel.guide.ui.home.profile.userFollowActivity.UserFollowActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CustomerProfileActivity extends AppCompatActivity implements CustomerProfileListener, View.OnClickListener {

    private CustomerProfilePresenter customerProfilePresenter;

    private TextView userName, nickName, following, follower, reaction, bio, bioBtn, followBtn;
    private FrameLayout loaderContainer;
    private LinearLayout bioContainer;
    private LottieAnimationView lottieLoader;
    private CircleImageView image;

    private int customerUserId;
    private String customerUserName;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        this.customerUserId = getIntent().getIntExtra("id", 0);

        initUI();

        if (customerUserId != 0) {
            customerProfilePresenter.getProfile(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new ProfileRequest(customerUserId));
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(customerUserId));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "first");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        //Sets whether analytics collection is enabled for this app on this device.
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);

        //Sets the minimum engagement time required before starting a session. The default value is 10000 (10 seconds). Let's make it 20 seconds just for the fun
        //Sets the duration of inactivity that terminates the current session. The default value is 1800000 (30 minutes).
        mFirebaseAnalytics.setSessionTimeoutDuration(500);

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        mFirebaseAnalytics.setUserId(String.valueOf(customerUserId));
    }

    private void initUI() {

        customerProfilePresenter = new CustomerProfilePresenter(this);

        ViewPager viewPager = findViewById(R.id.customer_view_pager);
        TabLayout tabLayout = findViewById(R.id.customer_profile_tab);

        CustomerPagerAdapter customerPagerAdapter = new CustomerPagerAdapter(getSupportFragmentManager());

        customerPagerAdapter.addFragment(new CustomerPhotoFragment());
        customerPagerAdapter.addFragment(new CustomerTourFragment());
        customerPagerAdapter.setCustomerUserId(customerUserId);

        viewPager.setAdapter(customerPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        (Objects.requireNonNull(tabLayout.getTabAt(0))).setIcon(R.drawable.profile_photos);
        (Objects.requireNonNull(tabLayout.getTabAt(1))).setIcon(R.drawable.profile_tour);

        ConstraintLayout followersContainer = findViewById(R.id.customer_profile_follow_container);
        followersContainer.setOnClickListener(this);

        ImageButton backBtn = findViewById(R.id.customer_profile_back_btn);
        backBtn.setOnClickListener(this);

        TextView report = findViewById(R.id.customer_profile_report);
        report.setOnClickListener(this);

        followBtn = findViewById(R.id.customer_profile_follow_btn);
        followBtn.setOnClickListener(this);

        userName = findViewById(R.id.customer_profile_name);
        nickName = findViewById(R.id.customer_profile_nickName);
        following = findViewById(R.id.customer_profile_following_count);
        follower = findViewById(R.id.customer_profile_followers_count);
        reaction = findViewById(R.id.customer_profile_reaction_count);
        image = findViewById(R.id.customer_profile_image);
        bio = findViewById(R.id.customer_bio_text);
        bioBtn = findViewById(R.id.customer_bio_btn);
        bioContainer = findViewById(R.id.customer_profile_bio);

        loaderContainer = findViewById(R.id.customer_profile_frame);
        lottieLoader = findViewById(R.id.loader_customer_profile);
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {

        ProfileResponse.Userinfo userInfo = profileResponse.getUserinfo();

        userName.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        this.customerUserName = userInfo.getName() + " " + userInfo.getLastname();

        nickName.setText(String.format("@%s", userInfo.getNickname()));
        following.setText(String.valueOf(userInfo.getFollowing()));
        follower.setText(String.valueOf(userInfo.getFollower()));
        reaction.setText(String.valueOf(userInfo.getReactions()));

        if (userInfo.getProfile_pic() != null)
            HelperMedia.loadCirclePhoto(this, userInfo.getProfile_pic(), image);

        if (userInfo.getFollow() == 1)
            followBtn.setText("Following");
        else
            followBtn.setText("Follow");

        if (userInfo.getBiography() != null) {
            bioContainer.setVisibility(View.VISIBLE);
            bioBtn.setVisibility(View.VISIBLE);
            bio.setText(userInfo.getBiography());
        } else {
            bioContainer.setVisibility(View.GONE);
            bioBtn.setVisibility(View.GONE);
        }

        loaderContainer.setVisibility(View.GONE);
        lottieLoader.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        loaderContainer.setVisibility(View.GONE);
        lottieLoader.setVisibility(View.GONE);

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
                Intent intent = new Intent(this, UserFollowActivity.class);
                intent.putExtra("user_name", customerUserName);
                intent.putExtra("customer_user_id", customerUserId);
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
