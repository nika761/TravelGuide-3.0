package com.example.travelguide.ui.home.profile.userFollowActivity.following;

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

import com.example.travelguide.R;
import com.example.travelguide.model.request.FollowingRequest;
import com.example.travelguide.model.response.FollowingResponse;
import com.example.travelguide.helper.HelperPref;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FollowingFragment extends Fragment implements FollowingFragmentListener {
    private Context context;
    private FollowingFragmentPresenter followingFragmentPresenter;
    private RecyclerView recyclerView;
    private int userId;

    public FollowingFragment(int userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_following, container, false);
        recyclerView = view.findViewById(R.id.following_recycler);
        followingFragmentPresenter = new FollowingFragmentPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followingFragmentPresenter.getFollowing(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowingRequest(userId));
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
            FollowingAdapter mAdapter = new FollowingAdapter(context, followingResponse.getFollowings());
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onGetFollowingError(String message) {

    }

    @Override
    public void onDestroy() {
        if (followingFragmentPresenter != null) {
            followingFragmentPresenter = null;
        }
        super.onDestroy();
    }
}
