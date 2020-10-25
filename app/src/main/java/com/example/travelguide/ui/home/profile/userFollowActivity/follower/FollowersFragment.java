package com.example.travelguide.ui.home.profile.userFollowActivity.follower;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.FollowersRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.FollowerResponse;
import com.example.travelguide.helper.HelperPref;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FollowersFragment extends Fragment implements FollowersFragmentListener {

    private Context context;
    private RecyclerView recyclerView;
    private FollowersFragmentPresenter followersFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_followers, container, false);
        recyclerView = view.findViewById(R.id.followers_recycler);
        followersFragmentPresenter = new FollowersFragmentPresenter(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followersFragmentPresenter.getFollowers(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
                new FollowersRequest(HelperPref.getCurrentUserId(context)));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onGetFollowers(FollowerResponse followerResponse) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new FollowersAdapter(followerResponse.getFollowers(), this));
    }

    @Override
    public void onGetFollowersError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFollowStart(int userId) {
        followersFragmentPresenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowRequest(userId));
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {

    }

    @Override
    public void onFollowError(String message) {

    }

    @Override
    public void onDestroy() {
        if (followersFragmentPresenter != null) {
            followersFragmentPresenter = null;
        }
        super.onDestroy();
    }
}
