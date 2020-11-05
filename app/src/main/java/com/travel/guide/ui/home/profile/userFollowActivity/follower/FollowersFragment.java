package com.travel.guide.ui.home.profile.userFollowActivity.follower;

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

import com.travel.guide.R;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.FollowersRequest;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.FollowerResponse;
import com.travel.guide.helper.HelperPref;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FollowersFragment extends Fragment implements FollowersFragmentListener {

    private Context context;
    private RecyclerView recyclerView;
    private FollowersFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_followers, container, false);
        recyclerView = view.findViewById(R.id.followers_recycler);
        presenter = new FollowersFragmentPresenter(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            if (getArguments().getInt("customer_user_id") != 0) {
                int customerUserId = getArguments().getInt("customer_user_id");
                presenter.getFollowers(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowersRequest(customerUserId));
            } else {
                presenter.getFollowers(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowersRequest(HelperPref.getUserId(context)));
            }
        } else {
            presenter.getFollowers(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowersRequest(HelperPref.getUserId(context)));
        }

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
        presenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowRequest(userId));
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {

    }

    @Override
    public void onFollowError(String message) {

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }

        if (context != null) {
            context = null;
        }
        super.onDestroy();
    }
}
