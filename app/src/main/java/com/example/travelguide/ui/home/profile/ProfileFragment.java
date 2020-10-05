package com.example.travelguide.ui.home.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.ui.customerUser.post.CustomerPhotoFragment;
import com.example.travelguide.ui.home.profile.changeLanguage.ChangeLangFragment;
import com.example.travelguide.ui.home.profile.editProfile.EditProfileFragment;
import com.example.travelguide.ui.home.profile.favorites.FavoritePostFragment;
import com.example.travelguide.ui.home.profile.posts.UserPostsFragment;
import com.example.travelguide.ui.home.profile.tours.UserToursFragment;
import com.example.travelguide.ui.home.profile.userFollowActivity.UserFollowActivity;
import com.example.travelguide.ui.home.HomePageActivity;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.helper.HelperUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.helper.HelperPref.FACEBOOK;
import static com.example.travelguide.helper.HelperPref.GOOGLE;
import static com.example.travelguide.helper.HelperPref.TRAVEL_GUIDE;
import static com.example.travelguide.helper.HelperUI.ABOUT;
import static com.example.travelguide.helper.HelperUI.POLICY;
import static com.example.travelguide.helper.HelperUI.TERMS;
import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileFragment extends Fragment implements ProfileFragmentListener {

    private TextView nickName, name, userBioText, drawerBtn, tourRequest, firstPhotoConent, editProfile,
            bioText, following, follower, reaction;
    private ImageButton bioVisibleBtn, bioInvisibleBtn;
    private CircleImageView userPrfImage;
    private ImageView userBioTextState;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private ProfileFragmentPresenter profileFragmentPresenter;
    private LottieAnimationView lottieAnimationView;
    private View myLayout;
    private ProfileResponse.Userinfo userinfo;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar.setTitle("");
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.addDrawerListener(toggle);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        NavigationView navigationView = view.findViewById(R.id.burger_navigation_view);
        navigationView.setNavigationItemSelectedListener(navListener);

        profileFragmentPresenter = new ProfileFragmentPresenter(this);

        lottieAnimationView = view.findViewById(R.id.loader_profile);

        myLayout = view.findViewById(R.id.test);
        viewPager = myLayout.findViewById(R.id.viewpager_test);
        tabLayout = myLayout.findViewById(R.id.tabs_test);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());

        this.userId = HelperPref.getCurrentUserId(context);
        profilePagerAdapter.addFragment(new UserPostsFragment(userId, context));
        profilePagerAdapter.addFragment(new FavoritePostFragment());
        profilePagerAdapter.addFragment(new UserToursFragment());

        viewPager.setAdapter(profilePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_liked);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile_tour);

        View followStates = myLayout.findViewById(R.id.states_test);
        followStates.setOnClickListener(this::onViewClick);

        editProfile = myLayout.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(this::onViewClick);

        bioVisibleBtn = myLayout.findViewById(R.id.bio_visible_btn);
        bioVisibleBtn.setOnClickListener(this::onViewClick);

        bioInvisibleBtn = myLayout.findViewById(R.id.bio_invisible_btn);
        bioInvisibleBtn.setOnClickListener(this::onViewClick);

        userPrfImage = myLayout.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.user_prf_name_toolbar);
        nickName = myLayout.findViewById(R.id.user_prf_nickName);
        follower = myLayout.findViewById(R.id.followers_count);
        following = myLayout.findViewById(R.id.following_count);
        reaction = myLayout.findViewById(R.id.reactions_count);
        bioText = myLayout.findViewById(R.id.bio_text);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottieAnimationView.setVisibility(View.VISIBLE);
        profileFragmentPresenter.getProfile(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
                new ProfileRequest(userId));

//        if (HelperPref.getUserProfileInfo(context) == null) {
//            lottieAnimationView.setVisibility(View.VISIBLE);
//            profileFragmentPresenter.getProfile(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
//                    new ProfileRequest(HelperPref.getServerSavedUsers(context).get(0).getId()));
//        } else {
//            this.userinfo = HelperPref.getUserProfileInfo(context);
//            myLayout.setVisibility(View.VISIBLE);
//            name.setText(userinfo.getName() + " " + userinfo.getLastname());
//            nickName.setText(String.format("@%s", userinfo.getNickname()));
//            bioText.setText(userinfo.getBiography());
//            follower.setText(String.valueOf(userinfo.getFollower()));
//            following.setText(String.valueOf(userinfo.getFollowing()));
//            reaction.setText(String.valueOf(userinfo.getReactions()));
//            this.userId = userinfo.getId();
//        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private NavigationView.OnNavigationItemSelectedListener navListener = item -> {

        switch (item.getItemId()) {
            case R.id.settings_share_profile:
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_language:
                ChangeLangFragment f = new ChangeLangFragment();
                f.show(getChildFragmentManager(), "fr");
                break;

            case R.id.settings_about:
                HelperUI.startTermsAndPolicyActivity(context, ABOUT);
                break;

            case R.id.settings_privacy:
                HelperUI.startTermsAndPolicyActivity(context, POLICY);
                break;

            case R.id.settings_terms:
                HelperUI.startTermsAndPolicyActivity(context, TERMS);
                break;

            case R.id.settings_sing_out:
                String type = HelperPref.getCurrentPlatform(context);
                logOut(type);
                break;
        }
        return true;
    };

    private void logOut(String loginType) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sign out ?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (loginType != null) {
                        switch (loginType) {
                            case FACEBOOK:
                                Toast.makeText(context, "Case facebook working", Toast.LENGTH_SHORT).show();
                                ((HomePageActivity) Objects.requireNonNull(getActivity())).signOutFromFbAccount();
                                break;
                            case GOOGLE:
                                Toast.makeText(context, "Case google working", Toast.LENGTH_SHORT).show();
                                ((HomePageActivity) Objects.requireNonNull(getActivity())).signOutFromGoogleAccount();
                                break;
                            case TRAVEL_GUIDE:
                                Toast.makeText(context, "Case TravelGuide working", Toast.LENGTH_SHORT).show();
                                ((HomePageActivity) Objects.requireNonNull(getActivity())).signOutFromAccount();
                                break;
                        }
                    } else {
                        Toast.makeText(context, "Case error", Toast.LENGTH_SHORT).show();
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.bio_visible_btn:
                bioText.setVisibility(View.VISIBLE);
                bioVisibleBtn.setVisibility(View.GONE);
                bioInvisibleBtn.setVisibility(View.VISIBLE);
                break;

            case R.id.bio_invisible_btn:
                bioText.setVisibility(View.GONE);
                bioVisibleBtn.setVisibility(View.VISIBLE);
                bioInvisibleBtn.setVisibility(View.GONE);
                break;

            case R.id.states_test:
                Intent intent = new Intent(context, UserFollowActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                break;

            case R.id.edit_profile:
                HelperUI.loadFragment(new EditProfileFragment(), null, R.id.user_page_frg_container, true, ((HomePageActivity) context));
                break;
        }
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {

        ProfileResponse.Userinfo userInfo;

        if (profileResponse.getStatus() == 0) {

            userInfo = profileResponse.getUserinfo();

            HelperPref.saveUserProfileInfo(context, userInfo);

            lottieAnimationView.setVisibility(View.GONE);
            myLayout.setVisibility(View.VISIBLE);

            name.setText(userInfo.getName() + " " + userInfo.getLastname());
            nickName.setText(String.format("@%s", userInfo.getNickname()));
            bioText.setText(userInfo.getBiography());
            follower.setText(String.valueOf(userInfo.getFollower()));
            following.setText(String.valueOf(userInfo.getFollowing()));
            reaction.setText(String.valueOf(userInfo.getReactions()));
            HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);
            this.userId = userInfo.getId();

        }
    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (profileFragmentPresenter != null) {
            profileFragmentPresenter = null;
        }
        super.onDestroy();
    }

    public interface OnPostChooseListener {
        void onPostChoose(List<PostResponse.Posts> posts);
    }


}
