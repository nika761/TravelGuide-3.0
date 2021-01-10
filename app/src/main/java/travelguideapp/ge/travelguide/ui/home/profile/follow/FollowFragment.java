package travelguideapp.ge.travelguide.ui.home.profile.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.FollowType;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;

import java.util.List;


public class FollowFragment extends Fragment implements FollowFragmentListener {

    public static FollowFragment getInstance(FollowFragmentCallbacks callback) {
        FollowFragment followFragment = new FollowFragment();
        followFragment.callback = callback;
        return followFragment;
    }

    private FollowFragmentPresenter presenter;
    private FollowRecyclerAdapter followRecyclerAdapter;
    private RecyclerView followRecycler;

    private FollowFragmentCallbacks callback;

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
        if (customerUserId != 0)
            followRecyclerAdapter.setIsCustomer(true);
        followRecycler = view.findViewById(R.id.follow_recycler);
        followRecycler.setLayoutManager(new LinearLayoutManager(followRecycler.getContext()));
        followRecycler.setHasFixedSize(true);

        presenter = new FollowFragmentPresenter(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
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
    public void onChooseUser(int userId) {
        if (callback != null)
            callback.onChooseUser(userId);
    }


    @Override
    public void onFollowAction(int userId, int position) {
        this.actionPosition = position;
        presenter.startAction(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowRequest(userId));
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
        MyToaster.getErrorToaster(followRecycler.getContext(), message);
    }

    private boolean checkRequestType() {
        return requestType != null;
    }

    private void getFollowData() {
        try {
            if (checkRequestType()) {
                switch (requestType) {
                    case FOLLOWER:
                        if (customerUserId > 0)
                            presenter.getFollowers(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowersRequest(customerUserId));
                        else
                            presenter.getFollowers(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowersRequest(GlobalPreferences.getUserId(followRecycler.getContext())));
                        break;

                    case FOLLOWING:
                        if (customerUserId > 0)
                            presenter.getFollowing(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowingRequest(customerUserId));
                        else
                            presenter.getFollowing(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowingRequest(GlobalPreferences.getUserId(followRecycler.getContext())));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    public interface FollowFragmentCallbacks {
        void onChooseUser(int userId);
    }

}
