package travelguideapp.ge.travelguide.ui.home.customerUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

import com.google.android.material.tabs.TabLayout;

import travelguideapp.ge.travelguide.ui.home.profile.ProfilePagerAdapter;
import travelguideapp.ge.travelguide.ui.home.profile.follow.FollowActivity;
import travelguideapp.ge.travelguide.ui.home.profile.posts.UserPostsFragment;
import travelguideapp.ge.travelguide.ui.home.profile.tours.UserToursFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomerProfileActivity extends BaseActivity implements CustomerProfileListener, OnPostChooseCallback,
        View.OnClickListener {

    private CustomerProfilePresenter customerProfilePresenter;

    private TextView userName, nickName, following, follower, reaction, bio, bioBtn, followBtn;
    private ConstraintLayout fragmentContainerMain;
    private LottieAnimationView lottieLoader;
    private FrameLayout loaderContainer;
    private LinearLayout bioContainer;
    private CircleImageView image;

    private String customerUserName;
    private int customerUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        try {
            this.customerUserId = getIntent().getIntExtra("id", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();

        if (customerUserId != 0)
            customerProfilePresenter.getProfile(GlobalPreferences.getAccessToken(this), new ProfileRequest(customerUserId));
        else
            onBackPressed();

//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(customerUserId));
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "first");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        bundle.putString(FirebaseAnalytics.Event.PURCHASE, "TESTING");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP, bundle);
//        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//        mFirebaseAnalytics.setSessionTimeoutDuration(500);
//        mFirebaseAnalytics.setUserId(String.valueOf(customerUserId));

    }

    private void initUI() {

        customerProfilePresenter = new CustomerProfilePresenter(this);

        ViewPager viewPager = findViewById(R.id.customer_view_pager);
        TabLayout tabLayout = findViewById(R.id.customer_profile_tab);

        fragmentContainerMain = findViewById(R.id.customer_profile_fragment_container_main);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
        profilePagerAdapter.setCustomerUserId(customerUserId);
        profilePagerAdapter.setCallback(this);
        profilePagerAdapter.setGetPostsFrom(GetPostsFrom.CUSTOMER_POSTS);
        profilePagerAdapter.addFragment(new UserPostsFragment());
        profilePagerAdapter.addFragment(new UserToursFragment());
        viewPager.setAdapter(profilePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_tour);

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
        try {
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
                followBtn.setText(getResources().getString(R.string.following));
            else
                followBtn.setText(getResources().getString(R.string.follow));

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        loaderContainer.setVisibility(View.GONE);
        lottieLoader.setVisibility(View.GONE);
        MyToaster.getErrorToaster(this, message);
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {
        switch (followResponse.getStatus()) {
            case 0:
                followBtn.setText(getResources().getString(R.string.following));
                break;

            case 1:
                followBtn.setText(getResources().getString(R.string.follow));
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_profile_back_btn:
                onBackPressed();
                finish();
                break;

            case R.id.customer_profile_report:
                openReportDialog();
                break;

            case R.id.customer_profile_follow_btn:
                customerProfilePresenter.follow(GlobalPreferences.getAccessToken(this), new FollowRequest(customerUserId));
                break;

            case R.id.customer_profile_follow_container:
                Intent intent = new Intent(this, FollowActivity.class);
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

    @Override
    public void onPostChoose(Bundle fragmentData) {
        fragmentContainerMain.setVisibility(View.VISIBLE);
        HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, true, true, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_activity_slide_in_left, R.anim.anim_activity_slide_out_rigth);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            try {
//                if (isReplyOpened){
//                    isReplyOpened = false;
//                }
//                if (commentFragmentContainer.getVisibility() == View.VISIBLE) {
//                    commentFragmentContainer.setVisibility(View.GONE);
//                } else if (fragmentContainerMain.getVisibility() == View.VISIBLE) {
//                    fragmentContainerMain.setVisibility(View.GONE);
//                } else {
//                    onBackPressed();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return super.onKeyDown(keyCode, event);
    }


}

