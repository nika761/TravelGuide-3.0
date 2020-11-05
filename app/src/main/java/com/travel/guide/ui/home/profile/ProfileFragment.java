package com.travel.guide.ui.home.profile;

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
import android.widget.LinearLayout;
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
import com.travel.guide.R;
import com.travel.guide.enums.Enums;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.ui.home.profile.changeLanguage.ChangeLangFragment;
import com.travel.guide.ui.home.profile.editProfile.ProfileEditActivity;
import com.travel.guide.ui.home.profile.favorites.FavoritePostFragment;
import com.travel.guide.ui.home.profile.posts.UserPostsFragment;
import com.travel.guide.ui.home.profile.tours.UserToursFragment;
import com.travel.guide.ui.home.profile.userFollowActivity.UserFollowActivity;
import com.travel.guide.ui.home.HomePageActivity;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.helper.HelperUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileFragment extends Fragment implements ProfileFragmentListener {

    private TextView nickName, name, userBioText, bioText, following, follower, reaction, bioHead;
    private ImageButton bioVisibleBtn, bioInvisibleBtn;
    private LottieAnimationView lottieAnimationView;
    private CircleImageView userPrfImage;
    private ActionBarDrawerToggle toggle;
    private ImageView userBioTextState;
    private Context context;
    private View myLayout;

    private LinearLayout bioContainer;
    private ProfileFragmentPresenter presenter;
    private String userName;
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

        presenter = new ProfileFragmentPresenter(this);

        lottieAnimationView = view.findViewById(R.id.loader_profile);

        myLayout = view.findViewById(R.id.test);
        ViewPager viewPager = myLayout.findViewById(R.id.viewpager_test);
        TabLayout tabLayout = myLayout.findViewById(R.id.tabs_test);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());

        this.userId = HelperPref.getUserId(context);
        profilePagerAdapter.addFragment(new UserPostsFragment());
        profilePagerAdapter.addFragment(new FavoritePostFragment());
        profilePagerAdapter.addFragment(new UserToursFragment());

        viewPager.setAdapter(profilePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_liked);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile_tour);

        View followStates = myLayout.findViewById(R.id.states_test);
        followStates.setOnClickListener(this::onViewClick);

        TextView editProfile = myLayout.findViewById(R.id.edit_profile);
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
        bioContainer = myLayout.findViewById(R.id.profile_bio);
        bioHead = myLayout.findViewById(R.id.profile_bio_head);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottieAnimationView.setVisibility(View.VISIBLE);
        presenter.getProfile(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new ProfileRequest(HelperPref.getUserId(context)));
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
                ChangeLangFragment changeLangFragment = new ChangeLangFragment();
                changeLangFragment.show(getChildFragmentManager(), "fr");
                break;

            case R.id.settings_about:
                HelperUI.startWebActivity(context, Enums.LoadWebViewType.ABOUT);
                break;

            case R.id.settings_privacy:
                HelperUI.startWebActivity(context, Enums.LoadWebViewType.POLICY);
                break;

            case R.id.settings_terms:
                HelperUI.startWebActivity(context, Enums.LoadWebViewType.TERMS);
                break;

            case R.id.settings_sing_out:
//                String type = HelperPref.getLoginType(context);
                logOutDialog();
                break;
        }
        return true;
    };

    private void logOutDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sign out ?")
                .setPositiveButton("Yes", (dialog, which) -> {
//                    if (loginType != null) {
//                        switch (loginType) {
//                            case FACEBOOK:
//                                ((HomePageActivity) context).signOutFromFbAccount();
//                                break;
//                            case GOOGLE:
//                                ((HomePageActivity) context).signOutFromGoogleAccount();
//                                break;
//                            case TRAVEL_GUIDE:
//                                ((HomePageActivity) context).signOutFromAccount();
//                                break;
//                        }
//                    } else {
//                        Toast.makeText(context, "Case error", Toast.LENGTH_SHORT).show();
//                        Objects.requireNonNull(getActivity()).finish();
//                    }
                    ((HomePageActivity) context).onLogOutChoose();

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
                intent.putExtra("user_name", userName);
                startActivity(intent);
                break;

            case R.id.edit_profile:
                Intent editProfile = new Intent(context, ProfileEditActivity.class);
                startActivity(editProfile);
                break;
        }
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {

        HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);

        lottieAnimationView.setVisibility(View.GONE);
        myLayout.setVisibility(View.VISIBLE);

        userName = userInfo.getName() + " " + userInfo.getLastname();
        name.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        nickName.setText(String.format("@%s", userInfo.getNickname()));

        if (userInfo.getBiography() != null) {
            bioContainer.setVisibility(View.VISIBLE);
            bioVisibleBtn.setVisibility(View.VISIBLE);
            bioHead.setVisibility(View.VISIBLE);
            bioText.setVisibility(View.VISIBLE);
            bioText.setText(userInfo.getBiography());
        } else {
            bioContainer.setVisibility(View.GONE);
            bioHead.setVisibility(View.GONE);
            bioText.setVisibility(View.GONE);
            bioVisibleBtn.setVisibility(View.GONE);
        }

        follower.setText(String.valueOf(userInfo.getFollower()));
        following.setText(String.valueOf(userInfo.getFollowing()));
        reaction.setText(String.valueOf(userInfo.getReactions()));

        this.userId = userInfo.getId();

    }

    @Override
    public void onGetError(String message) {
        lottieAnimationView.setVisibility(View.GONE);

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    public interface OnPostChooseListener {
        void onPostChoose(Bundle fragmentData);
    }


}
