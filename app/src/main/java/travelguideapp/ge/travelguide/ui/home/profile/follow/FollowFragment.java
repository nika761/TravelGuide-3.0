package travelguideapp.ge.travelguide.ui.home.profile.follow;

import android.os.Bundle;
import android.os.Handler;
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

    private FollowRecyclerAdapter followRecyclerAdapter;
    private FollowFragmentPresenter presenter;
    private RecyclerView followRecycler;

    private FollowFragmentCallbacks callback;
    private FollowType requestType;

    private List<FollowingResponse.Followings> followingsInner;
    private List<FollowingResponse.Followings> followingsMain;
    private List<FollowerResponse.Followers> followersInner;
    private List<FollowerResponse.Followers> followersMain;

    private int customerUserId;
    private int actionPosition;

    private boolean isLoading;
    private boolean isNeedLazyLoad;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenToLazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            getFollowData();
    }

    private void listenToLazyLoad() {
        followRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (!isLoading && isNeedLazyLoad) {
                        switch (requestType) {
                            case FOLLOWER:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followingsInner.size() - 1) {
                                    lazyLoad();
                                    isLoading = true;
                                }
                                break;
                            case FOLLOWING:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followersInner.size() - 1) {
                                    lazyLoad();
                                    isLoading = true;
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void lazyLoad() {
        try {
            switch (requestType) {
                case FOLLOWER:
                    Handler handler1 = new Handler();
                    handler1.postDelayed(() -> {
                        int currentSize = followersInner.size();
                        int nextLimit = currentSize + 15;

                        while (currentSize - 1 < nextLimit && currentSize < followersMain.size()) {
                            followersInner.add(followersMain.get(currentSize));
                            currentSize++;
                        }

                        followRecyclerAdapter.notifyDataSetChanged();

                        isLoading = false;
                    }, 300);
                    break;

                case FOLLOWING:
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        int currentSize = followingsInner.size();
                        int nextLimit = currentSize + 15;

                        while (currentSize - 1 < nextLimit && currentSize < followingsMain.size()) {
                            followingsInner.add(followingsMain.get(currentSize));
                            currentSize++;
                        }

                        followRecyclerAdapter.notifyDataSetChanged();

                        isLoading = false;
                    }, 300);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGetFollowData(Object object) {
        try {
            switch (requestType) {
                case FOLLOWING:
                    List<FollowingResponse.Followings> followings = ((FollowingResponse) object).getFollowings();
                    this.followingsMain = followings;
                    if (followings.size() > 15) {
                        isNeedLazyLoad = true;
                        for (int i = 0; i < 15; i++) {
                            followingsInner.add(followingsMain.get(i));
                        }
                        followRecyclerAdapter.setFollowings(followingsInner);
                    } else {
                        isNeedLazyLoad = false;
                        followRecyclerAdapter.setFollowings(followingsMain);
                    }
                    break;

                case FOLLOWER:
                    List<FollowerResponse.Followers> followers = ((FollowerResponse) object).getFollowers();
                    this.followersMain = followers;
                    if (followers.size() > 15) {
                        isNeedLazyLoad = true;
                        for (int i = 0; i < 15; i++) {
                            followersInner.add(followersMain.get(i));
                        }
                        followRecyclerAdapter.setFollowers(followersInner);
                    } else {
                        isNeedLazyLoad = false;
                        followRecyclerAdapter.setFollowers(followersMain);
                    }
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
