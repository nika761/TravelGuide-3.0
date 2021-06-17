package travelguideapp.ge.travelguide.ui.profile.follow;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseFragment;
import travelguideapp.ge.travelguide.enums.FollowType;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.listener.QuestionDialogListener;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;

public class FollowFragment extends BaseFragment<FollowFragmentPresenter> implements FollowFragmentListener {

    public static FollowFragment getInstance() {
        return new FollowFragment();
    }

    private FollowRecyclerAdapter followRecyclerAdapter;
    private RecyclerView followRecycler;

    private FollowType followType;

    private List<FollowingResponse.Followings> followingsInner;
    private List<FollowingResponse.Followings> followingsMain;

    private List<FollowerResponse.Followers> followersInner;
    private List<FollowerResponse.Followers> followersMain;

    private int customerUserId;
    private int actionPosition;

    private boolean isLoading = false;
    private boolean isFirstTime = true;
    private boolean isNeedLazyLoad;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        getExtras();

        followRecyclerAdapter = new FollowRecyclerAdapter(this);

        if (customerUserId != 0)
            followRecyclerAdapter.setIsCustomer(true);

        followRecycler = view.findViewById(R.id.follow_recycler);
        followRecycler.setLayoutManager(new LinearLayoutManager(followRecycler.getContext()));
        followRecycler.setHasFixedSize(true);

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenToLazyLoad();
    }

    @Override
    public void onStart() {
        super.onStart();
        attachPresenter(FollowFragmentPresenter.with(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowersAndFollowings();
    }

    private void getExtras() {
        try {
            this.followType = (FollowType) getArguments().getSerializable("request_type");
            this.customerUserId = getArguments().getInt("customer_user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (followType == null) {
            try {
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (followType) {
            case FOLLOWING:
                followingsInner = new ArrayList<>();
                break;
            case FOLLOWER:
                followersInner = new ArrayList<>();
                break;
        }
    }

    private void listenToLazyLoad() {
        followRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (!isLoading && isNeedLazyLoad) {
                        switch (followType) {
                            case FOLLOWER:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followersInner.size() - 1) {
                                    isLoading = true;
                                    onLazyLoad();
                                }
                                break;
                            case FOLLOWING:
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == followingsInner.size() - 1) {
                                    isLoading = true;
                                    onLazyLoad();
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

    private void onLazyLoad() {
        try {
            switch (followType) {
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
            switch (followType) {
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
            followRecycler.setAdapter(followRecyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChooseUser(int userId) {
        try {
            if (GlobalPreferences.getUserId() != userId) {
                startActivity(CustomerProfileActivity.getCustomerIntent(getActivity(), userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStartFollowing(int userId, int position) {
        try {
            this.actionPosition = position;
            String question = getString(R.string.follow) + " ?";
            DialogManager.questionDialog(followRecycler.getContext(), question, questionDialogListener(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStopFollowing(String userName, int userId, int position) {
        try {
            this.actionPosition = position;
            String question = getString(R.string.unfollow) + " " + userName + " ?";
            DialogManager.questionDialog(followRecycler.getContext(), question, questionDialogListener(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private QuestionDialogListener questionDialogListener(int userId) {
        return () -> {
            if (presenter != null) {
                presenter.startAction(new FollowRequest(userId));
            }
        };
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

    private void getFollowersAndFollowings() {
        if (presenter != null) {
            if (isFirstTime) {
                isFirstTime = false;
                try {
                    switch (followType) {
                        case FOLLOWER:
                            if (customerUserId > 0)
                                presenter.getFollowers(new FollowersRequest(customerUserId));
                            else
                                presenter.getFollowers(new FollowersRequest(GlobalPreferences.getUserId()));
                            break;

                        case FOLLOWING:
                            if (customerUserId > 0)
                                presenter.getFollowing(new FollowingRequest(customerUserId));
                            else
                                presenter.getFollowing(new FollowingRequest(GlobalPreferences.getUserId()));
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
