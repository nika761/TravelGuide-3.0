package com.travel.guide.ui.home.profile.userFollowActivity.following;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.model.request.FollowingRequest;
import com.travel.guide.model.response.FollowingResponse;
import com.travel.guide.helper.HelperPref;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FollowingFragment extends Fragment implements FollowingFragmentListener {
    private Context context;
    private FollowingFragmentPresenter followingPresenter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_following, container, false);
        recyclerView = view.findViewById(R.id.following_recycler);
        followingPresenter = new FollowingFragmentPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getInt("customer_user_id") != 0) {
                int customerUserId = getArguments().getInt("customer_user_id");
                followingPresenter.getFollowing(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowingRequest(customerUserId));
            } else {
                followingPresenter.getFollowing(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowingRequest(HelperPref.getUserId(context)));
            }
        } else {
            followingPresenter.getFollowing(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowingRequest(HelperPref.getUserId(context)));
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onGetFollowing(FollowingResponse followingResponse) {
        if (followingResponse.getFollowings() != null && followingResponse.getFollowings().size() != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new FollowingAdapter(followingResponse.getFollowings()));
        }
    }

    @Override
    public void onGetFollowingError(String message) {

    }

    @Override
    public void onDestroy() {
        if (followingPresenter != null) {
            followingPresenter = null;
        }

        if (context != null) {
            context = null;
        }
        super.onDestroy();
    }
}
