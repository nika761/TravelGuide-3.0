package travelguideapp.ge.travelguide.ui.home.profile.follow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.FollowType;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FollowType requestType;
    private FollowFragmentListener listener;

    private boolean isCustomer;

    private List<FollowerResponse.Followers> followers;
    private List<FollowingResponse.Followings> followings;

    FollowRecyclerAdapter(FollowFragmentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (requestType == FollowType.FOLLOWING)
            return new FollowRecyclerAdapter.FollowingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false));
        else
            return new FollowRecyclerAdapter.FollowerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (requestType) {
            case FOLLOWING:
                ((FollowRecyclerAdapter.FollowingHolder) holder).bindView(position);
                break;
            case FOLLOWER:
                ((FollowRecyclerAdapter.FollowerHolder) holder).bindView(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (requestType == FollowType.FOLLOWING)
            return followings.size();
        else
            return followers.size();
    }

    void setFollowers(List<FollowerResponse.Followers> followers) {
        this.followers = followers;
        this.requestType = FollowType.FOLLOWER;
        notifyDataSetChanged();
    }

    void setFollowings(List<FollowingResponse.Followings> followings) {
        this.followings = followings;
        this.requestType = FollowType.FOLLOWING;
        notifyDataSetChanged();
    }

    public void setIsCustomer(boolean customer) {
        isCustomer = customer;
    }

    void followed(int position) {
        if (requestType == FollowType.FOLLOWER) {
            followers.get(position).setIs_following(1);
            notifyItemChanged(position);
        }
    }

    void unFollowed(int position) {
        switch (requestType) {
            case FOLLOWING:
                followings.remove(position);
                notifyDataSetChanged();
                break;

            case FOLLOWER:
                followers.get(position).setIs_following(0);
                notifyItemChanged(position);
                break;
        }
    }

    class FollowerHolder extends RecyclerView.ViewHolder {

        Animation animation;
        CircleImageView userImage;
        TextView userName, nickName, followBtn;

        FollowerHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.followers_user_name);
            nickName = itemView.findViewById(R.id.followers_user_nick_name);

            userImage = itemView.findViewById(R.id.followers_user_image);
            userImage.setOnClickListener(v -> listener.onChooseUser(followers.get(getLayoutPosition()).getUser_id()));

            followBtn = itemView.findViewById(R.id.followers_user_follow);
            followBtn.setOnClickListener(v -> listener.onFollowAction(followers.get(getLayoutPosition()).getUser_id(), getLayoutPosition()));
//            followBtn.setOnClickListener(v -> {
//                if (followers.get(getLayoutPosition()).getIs_following() == 1) {
//
//                }
//            });

//            animation = AnimationUtils.loadAnimation(nickName.getContext(), R.anim.anim_follow_item_up);

        }

        void bindView(int position) {
            try {
                if (position == followers.size() - 1) {

                }

//            itemView.startAnimation(animation);

                HelperMedia.loadCirclePhoto(followBtn.getContext(), followers.get(position).getProfile_pic(), userImage);

                userName.setText(followers.get(position).getName());
                nickName.setText(followers.get(position).getNickname());

                if (followers.get(position).getIs_following() == 1)
                    followBtn.setText(followBtn.getContext().getResources().getString(R.string.following));
                else
                    followBtn.setText(followBtn.getContext().getResources().getString(R.string.follow));

                if (followers.get(position).getUser_id() == GlobalPreferences.getUserId(userImage.getContext()))
                    followBtn.setVisibility(View.GONE);

                if (isCustomer)
                    followBtn.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class FollowingHolder extends RecyclerView.ViewHolder {

        Animation animation;
        CircleImageView userImage;
        TextView userName, userNickName, unFollow;

        FollowingHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.following_user_name);
            userNickName = itemView.findViewById(R.id.following_user_nick_name);

            userImage = itemView.findViewById(R.id.following_user_image);
            userImage.setOnClickListener(v -> listener.onChooseUser(followings.get(getLayoutPosition()).getUser_id()));

            unFollow = itemView.findViewById(R.id.following_user_unfollow);
            unFollow.setOnClickListener(v -> listener.onFollowAction(followings.get(getLayoutPosition()).getUser_id(), getLayoutPosition()));
//            animation = AnimationUtils.loadAnimation(userName.getContext(), R.anim.anim_follow_item_up);
            if (isCustomer)
                unFollow.setVisibility(View.GONE);
        }


        void bindView(int position) {
            try {
//                itemView.startAnimation(animation);
                if (followings.get(position).getUser_id() == GlobalPreferences.getUserId(userImage.getContext()))
                    unFollow.setVisibility(View.GONE);

                HelperMedia.loadCirclePhoto(userName.getContext(), followings.get(position).getProfile_pic(), userImage);
                userName.setText(followings.get(position).getName());
                userNickName.setText(followings.get(position).getNickname());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
