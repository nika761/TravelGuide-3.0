package com.example.travelguide.ui.home.profile.userFollowActivity.follower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.FollowerResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {
    private List<FollowerResponse.Followers> followers;
    private FollowersFragmentListener followerFragmentListener;

    FollowersAdapter(List<FollowerResponse.Followers> followers, FollowersFragmentListener followerFragmentListener) {
        this.followers = followers;
        this.followerFragmentListener = followerFragmentListener;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false);
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        HelperMedia.loadCirclePhoto(holder.followBtn.getContext(), followers.get(position).getProfile_pic(), holder.userImage);
        holder.userName.setText(followers.get(position).getName());
        holder.nickName.setText(followers.get(position).getNickname());
        if (followers.get(position).getIs_following() == 1) {
            holder.followBtn.setVisibility(View.GONE);
        } else {
            holder.followBtn.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return followers.size();
    }

    class FollowersViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView userName, nickName, followBtn;


        FollowersViewHolder(@NonNull View itemView) {
            super(itemView);
            iniUI(itemView);
        }


        private void iniUI(View view) {
            userImage = view.findViewById(R.id.followers_user_image);
            userName = view.findViewById(R.id.followers_user_name);
            nickName = view.findViewById(R.id.followers_user_nick_name);

            followBtn = view.findViewById(R.id.followers_user_follow);
            followBtn.setOnClickListener(v -> followerFragmentListener.onFollowStart(followers.get(getLayoutPosition()).getUser_id()));
        }
    }


}
