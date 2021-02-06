package travelguideapp.ge.travelguide.ui.home.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.model.parcelable.PostDataLoad;
import travelguideapp.ge.travelguide.ui.home.profile.favorites.FavoritePostFragment;
import travelguideapp.ge.travelguide.ui.home.profile.posts.UserPostsFragment;
import travelguideapp.ge.travelguide.ui.home.profile.tours.UserToursFragment;

import java.util.ArrayList;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private PostDataLoad.Source loadSource;
    private OnPostChooseCallback callback;
    private int customerUserId;

    public ProfilePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (loadSource == PostDataLoad.Source.CUSTOMER_POSTS) {
            Bundle data = new Bundle();
            data.putInt("customer_user_id", customerUserId);
            data.putSerializable("request_type", loadSource);
            switch (position) {
                case 0:
                    UserPostsFragment userPostsFragment = UserPostsFragment.getInstance(callback);
                    userPostsFragment.setArguments(data);
                    return userPostsFragment;
                case 1:
                    UserToursFragment userToursFragment = new UserToursFragment();
                    userToursFragment.setArguments(data);
                    return userToursFragment;
            }
        } else {
            switch (position) {
                case 0:
                    return UserPostsFragment.getInstance(callback);
                case 1:
                    return FavoritePostFragment.getInstance(callback);
                case 2:
                    return new UserToursFragment();
            }
        }
        return fragments.get(0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public void setLoadSource(PostDataLoad.Source loadSource) {
        this.loadSource = loadSource;
    }

    public void setCustomerUserId(int customerUserId) {
        this.customerUserId = customerUserId;
    }

    public void setCallback(OnPostChooseCallback callback) {
        this.callback = callback;
    }

}
