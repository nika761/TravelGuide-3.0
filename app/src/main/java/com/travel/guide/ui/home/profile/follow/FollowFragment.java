package com.travel.guide.ui.home.profile.follow;

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
import com.travel.guide.enums.FollowType;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.FollowersRequest;
import com.travel.guide.model.request.FollowingRequest;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.FollowerResponse;
import com.travel.guide.model.response.FollowingResponse;

import java.util.List;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FollowFragment extends Fragment implements FollowFragmentListener {

    private FollowFragmentPresenter presenter;
    private RecyclerView followRecycler;
    private FollowRecyclerAdapter followRecyclerAdapter;

    private FollowType requestType;

    private int customerUserId;
    private int actionPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        try {
            this.requestType = (FollowType) getArguments().getSerializable("request_type");

            this.customerUserId = getArguments().getInt("customer_user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        followRecyclerAdapter = new FollowRecyclerAdapter(this);
        followRecycler = view.findViewById(R.id.follow_recycler);
        followRecycler.setLayoutManager(new LinearLayoutManager(followRecycler.getContext()));
        followRecycler.setHasFixedSize(true);

        presenter = new FollowFragmentPresenter(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFollowData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowData();
    }

    @Override
    public void onGetFollowData(Object object) {
        try {
            switch (requestType) {
                case FOLLOWING:
                    List<FollowingResponse.Followings> followings = ((FollowingResponse) object).getFollowings();
                    followRecyclerAdapter.setFollowings(followings);
                    break;
                case FOLLOWER:
                    List<FollowerResponse.Followers> followers = ((FollowerResponse) object).getFollowers();
                    followRecyclerAdapter.setFollowers(followers);
                    break;
            }
            followRecycler.setAdapter(followRecyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFollowAction(int userId, int position) {
        this.actionPosition = position;
        presenter.startAction(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowRequest(userId));
    }

    @Override
    public void onFollowActionDone(FollowResponse followResponse) {
        try {
            switch (followResponse.getStatus()) {
                case 0:
                    followRecyclerAdapter.followed(actionPosition);
                    break;
                case 1:
                    followRecyclerAdapter.unFollowed(actionPosition);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(followRecycler.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkRequestType() {
        return requestType != null;
    }

    private void getFollowData() {
        if (checkRequestType()) {
            switch (requestType) {
                case FOLLOWER:
                    if (customerUserId > 0)
                        presenter.getFollowers(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowersRequest(customerUserId));
                    else
                        presenter.getFollowers(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowersRequest(GlobalPreferences.getUserId(followRecycler.getContext())));
                    break;

                case FOLLOWING:
                    if (customerUserId > 0)
                        presenter.getFollowing(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowingRequest(customerUserId));
                    else
                        presenter.getFollowing(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowingRequest(GlobalPreferences.getUserId(followRecycler.getContext())));
                    break;
            }
        } else {
            Toast.makeText(followRecycler.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        if (presenter != null) {
            presenter = null;
        }
        super.onStop();
    }
}
