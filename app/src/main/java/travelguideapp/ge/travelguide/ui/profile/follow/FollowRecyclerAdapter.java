package travelguideapp.ge.travelguide.ui.profile.follow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.FollowType;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FollowFragmentListener listener;
    private FollowType pageType;

    private List<FollowingResponse.Followings> followings;
    private List<FollowerResponse.Followers> followers;

    private final int VIEW_TYPE_FOLLOWER = 0;
    private final int VIEW_TYPE_FOLLOWING = 1;
    private final int VIEW_TYPE_LOADING = 2;
    private boolean isCustomer;

    FollowRecyclerAdapter(FollowFragmentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FOLLOWER:
                return new FollowRecyclerAdapter.FollowerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false));

            case VIEW_TYPE_FOLLOWING:
                return new FollowRecyclerAdapter.FollowingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false));

            case VIEW_TYPE_LOADING:
                return new FollowRecyclerAdapter.LazyLoaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lazy_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FollowerHolder) {
            ((FollowRecyclerAdapter.FollowerHolder) holder).onBind(position);
        } else if (holder instanceof FollowingHolder) {
            ((FollowRecyclerAdapter.FollowingHolder) holder).onBind(position);
        } else {
            ((FollowRecyclerAdapter.LazyLoaderHolder) holder).onBind();
        }
    }

    @Override
    public int getItemCount() {
        if (pageType == FollowType.FOLLOWING)
            return followings.size();
        else
            return followers.size();
    }


    @Override
    public int getItemViewType(int position) {
        switch (pageType) {
            case FOLLOWER:
                if (followers.get(position) == null) {
                    return VIEW_TYPE_LOADING;
                } else {
                    return VIEW_TYPE_FOLLOWER;
                }

            case FOLLOWING:
                if (followings.get(position) == null) {
                    return VIEW_TYPE_LOADING;
                } else {
                    return VIEW_TYPE_FOLLOWING;
                }
        }
        return 2;
    }

    void setFollowers(List<FollowerResponse.Followers> followers) {
        this.followers = followers;
        this.pageType = FollowType.FOLLOWER;
        notifyDataSetChanged();
    }

    void setFollowings(List<FollowingResponse.Followings> followings) {
        this.followings = followings;
        this.pageType = FollowType.FOLLOWING;
        notifyDataSetChanged();
    }

    public void setIsCustomer(boolean customer) {
        this.isCustomer = customer;
    }

    void followed(int position) {
        switch (pageType) {
            case FOLLOWING:
                return;

            case FOLLOWER:
                followers.get(position).setIs_following(1);
                notifyItemChanged(position);
                break;
        }
    }

    void unFollowed(int position) {
        switch (pageType) {
            case FOLLOWING:
                followings.remove(position);
//                notifyItemRemoved(position);
                notifyDataSetChanged();
                break;

            case FOLLOWER:
                followers.get(position).setIs_following(0);
                notifyItemChanged(position);
                break;
        }
    }

    private class FollowerHolder extends RecyclerView.ViewHolder {

        Animation animation;
        CircleImageView userImage;
        TextView userName, nickName, followBtn;
        ConstraintLayout mainContainer;

        FollowerHolder(@NonNull View itemView) {
            super(itemView);
            mainContainer = itemView.findViewById(R.id.followers_container);
            mainContainer.setOnClickListener(v -> listener.onChooseUser(followers.get(getLayoutPosition()).getUser_id()));

            userName = itemView.findViewById(R.id.followers_user_name);
            nickName = itemView.findViewById(R.id.followers_user_nick_name);

            userImage = itemView.findViewById(R.id.followers_user_image);

            followBtn = itemView.findViewById(R.id.followers_user_follow);
            followBtn.setOnClickListener(v -> {
                        FollowerResponse.Followers current = followers.get(getLayoutPosition());
                        if (current.getIs_following() == 1) {
                            listener.onStopFollowing(current.getName(), current.getUser_id(), getLayoutPosition());
                        } else {
                            listener.onStartFollowing(current.getUser_id(), getLayoutPosition());
                        }
                    }
            );
//            animation = AnimationUtils.loadAnimation(nickName.getContext(), R.anim.anim_follow_item_up);
        }

        void onBind(int position) {
            try {
//            itemView.startAnimation(animation);
                HelperMedia.loadCirclePhoto(followBtn.getContext(), followers.get(position).getProfile_pic(), userImage);

                userName.setText(followers.get(position).getName());
                nickName.setText(followers.get(position).getNickname());

                if (followers.get(position).getIs_following() == 1)
                    followBtn.setText(followBtn.getContext().getResources().getString(R.string.following));
                else
                    followBtn.setText(followBtn.getContext().getResources().getString(R.string.follow));

                if (followers.get(position).getUser_id() == GlobalPreferences.getUserId())
                    followBtn.setVisibility(View.GONE);

                if (isCustomer)
                    followBtn.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class FollowingHolder extends RecyclerView.ViewHolder {

        Animation animation;
        CircleImageView userImage;
        TextView userName, userNickName, unFollow;
        ConstraintLayout mainContainer;

        FollowingHolder(@NonNull View itemView) {
            super(itemView);

            mainContainer = itemView.findViewById(R.id.following_container);
            mainContainer.setOnClickListener(v -> listener.onChooseUser(followings.get(getLayoutPosition()).getUser_id()));

            userName = itemView.findViewById(R.id.following_user_name);
            userNickName = itemView.findViewById(R.id.following_user_nick_name);

            userImage = itemView.findViewById(R.id.following_user_image);

            unFollow = itemView.findViewById(R.id.following_user_unfollow);
            unFollow.setOnClickListener(v -> listener.onStopFollowing(followings.get(getLayoutPosition()).getName(), followings.get(getLayoutPosition()).getUser_id(), getLayoutPosition()));
//            animation = AnimationUtils.loadAnimation(userName.getContext(), R.anim.anim_follow_item_up);
            if (isCustomer)
                unFollow.setVisibility(View.GONE);
        }


        void onBind(int position) {
            try {
//                itemView.startAnimation(animation);
                if (followings.get(position).getUser_id() == GlobalPreferences.getUserId())
                    unFollow.setVisibility(View.GONE);

                HelperMedia.loadCirclePhoto(userName.getContext(), followings.get(position).getProfile_pic(), userImage);
                userName.setText(followings.get(position).getName());
                userNickName.setText(followings.get(position).getNickname());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class LazyLoaderHolder extends RecyclerView.ViewHolder {

        LottieAnimationView animation;

        LazyLoaderHolder(@NonNull View itemView) {
            super(itemView);
            animation = itemView.findViewById(R.id.item_lazy_loader);
        }

        void onBind() {
            animation.setVisibility(View.VISIBLE);
        }

    }

}
