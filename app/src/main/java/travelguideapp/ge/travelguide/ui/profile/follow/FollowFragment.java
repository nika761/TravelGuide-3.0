package travelguideapp.ge.travelguide.ui.profile.follow;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.callback.AskingDialogCallback;
import travelguideapp.ge.travelguide.enums.FollowType;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;

import java.util.ArrayList;
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

    private boolean isLoading = false;
    private boolean isFirstTime = true;
    private boolean isNeedLazyLoad;

    private LottieAnimationView loaderAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        initData();

        followRecyclerAdapter = new FollowRecyclerAdapter(this);
        if (customerUserId != 0)
            followRecyclerAdapter.setIsCustomer(true);

        followRecycler = view.findViewById(R.id.follow_recycler);
        followRecycler.setLayoutManager(new LinearLayoutManager(followRecycler.getContext()));
        followRecycler.setHasFixedSize(true);
        presenter = new FollowFragmentPresenter(this);

        loaderAnimation = view.findViewById(R.id.item_follow_loading);

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
        getFollowData();
    }

    private void listenToLazyLoad() {
        followRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (!isLoading && isNeedLazyLoad) {
                        switch (requestType) {
                            case FOLLOWER:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followersInner.size() - 1) {
                                    isLoading = true;
                                    lazyLoad();
                                }
                                break;
                            case FOLLOWING:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followingsInner.size() - 1) {
                                    isLoading = true;
                                    lazyLoad();
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

    private void initData() {
        try {
            this.requestType = (FollowType) getArguments().getSerializable("request_type");
            this.customerUserId = getArguments().getInt("customer_user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (requestType == null) {
            getActivity().finish();
        }

        switch (requestType) {
            case FOLLOWING:
                followingsInner = new ArrayList<>();
                break;
            case FOLLOWER:
                followersInner = new ArrayList<>();
                break;
        }
    }

    private void lazyLoad() {
        try {
            switch (requestType) {
                case FOLLOWER:
                    try {
                        followersInner.add(null);
                        followRecyclerAdapter.notifyItemInserted(followersInner.size() - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new Handler().postDelayed(() -> {
                        try {
                            followersInner.remove(followersInner.size() - 1);
                            followRecyclerAdapter.notifyItemRemoved(followersInner.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 800);

                    new Handler().postDelayed(() -> {
                        try {
                            int currentSize = followersInner.size();
                            int nextLimit = currentSize + 15;

                            while (currentSize - 1 < nextLimit && currentSize < followersMain.size()) {
                                followersInner.add(followersMain.get(currentSize));
                                currentSize++;
                            }

                            followRecyclerAdapter.notifyDataSetChanged();

                            isLoading = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 1000);
                    break;

                case FOLLOWING:
                    try {
                        followingsInner.add(null);
                        followRecyclerAdapter.notifyItemInserted(followingsInner.size() - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new Handler().postDelayed(() -> {
                        try {
                            followingsInner.remove(followingsInner.size() - 1);
                            followRecyclerAdapter.notifyItemRemoved(followingsInner.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 800);

                    new Handler().postDelayed(() -> {
                        try {
                            int currentSize = followingsInner.size();
                            int nextLimit = currentSize + 15;

                            while (currentSize - 1 < nextLimit && currentSize < followingsMain.size()) {
                                followingsInner.add(followingsMain.get(currentSize));
                                currentSize++;
                            }

                            followRecyclerAdapter.notifyDataSetChanged();

                            isLoading = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 1000);
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
                    if (followings.size() > 15) {
                        isNeedLazyLoad = true;
                        this.followingsMain = followings;
                        for (int i = 0; i < 15; i++) {
                            this.followingsInner.add(followingsMain.get(i));
                        }
                        followRecyclerAdapter.setFollowings(followingsInner);
                    } else {
                        isNeedLazyLoad = false;
                        followRecyclerAdapter.setFollowings(followings);
                    }
                    break;

                case FOLLOWER:
                    List<FollowerResponse.Followers> followers = ((FollowerResponse) object).getFollowers();
                    if (followers.size() > 15) {
                        isNeedLazyLoad = true;
                        this.followersMain = followers;
                        for (int i = 0; i < 15; i++) {
                            this.followersInner.add(followersMain.get(i));
                        }
                        followRecyclerAdapter.setFollowers(followersInner);
                    } else {
                        isNeedLazyLoad = false;
                        followRecyclerAdapter.setFollowers(followers);
                    }
                    break;
            }
            loaderAnimation.setVisibility(View.GONE);
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
        DialogManager.getAskingDialog(followRecycler.getContext(), getString(R.string.unfollow) + "?", () -> presenter.startAction(GlobalPreferences.getAccessToken(followRecycler.getContext()), new FollowRequest(userId)));
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
        loaderAnimation.setVisibility(View.GONE);
        MyToaster.getToast(followRecycler.getContext(), message);
    }

    private void getFollowData() {
        if (presenter != null) {
            if (isFirstTime) {
                isFirstTime = false;
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
