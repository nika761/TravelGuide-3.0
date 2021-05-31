package travelguideapp.ge.travelguide.ui.profile.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import travelguideapp.ge.travelguide.listener.PostChooseListener;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;


public class PostPagerAdapter extends FragmentStatePagerAdapter {

    private final PostHomeParams.Type loadPageType;
    private PostChooseListener callback;
    private int customerUserId;

    public PostPagerAdapter(@NonNull FragmentManager fragmentManager, PostHomeParams.Type loadPageType) {
        super(fragmentManager);
        this.loadPageType = loadPageType;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle data;
        switch (loadPageType) {
            case SEARCH:
                break;

            case FAVORITES:
            case MY_POSTS:
                if (position == 0) {
                    data = new Bundle();
                    data.putSerializable("request_type", PostHomeParams.Type.MY_POSTS);
                    PostsFragment userPostsFragment = PostsFragment.getInstance(callback);
                    userPostsFragment.setArguments(data);
                    return userPostsFragment;
                }
                if (position == 1) {
                    data = new Bundle();
                    data.putSerializable("request_type", PostHomeParams.Type.FAVORITES);
                    PostsFragment userPostsFragment = PostsFragment.getInstance(callback);
                    userPostsFragment.setArguments(data);
                    return userPostsFragment;
                }
                break;

            case CUSTOMER_POSTS:
                data = new Bundle();
                data.putInt("customer_user_id", customerUserId);
                data.putSerializable("request_type", loadPageType);
                if (position == 0) {
                    PostsFragment userPostsFragment = PostsFragment.getInstance(callback);
                    userPostsFragment.setArguments(data);
                    return userPostsFragment;
                }
                break;
        }
        return new PostsFragment();
    }

    @Override
    public int getCount() {
        switch (loadPageType) {
            case CUSTOMER_POSTS:
                return 1;

            case MY_POSTS:
            case FAVORITES:
                return 2;

            case SEARCH:
                break;
        }
        return 0;
    }

    public void setCustomerUserId(int customerUserId) {
        this.customerUserId = customerUserId;
    }

    public void setCallback(PostChooseListener callback) {
        this.callback = callback;
    }

}