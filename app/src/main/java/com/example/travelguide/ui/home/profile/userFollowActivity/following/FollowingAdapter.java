package com.example.travelguide.ui.home.profile.userFollowActivity.following;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.FollowingResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private List<FollowingResponse.Followings> followings;

    FollowingAdapter(List<FollowingResponse.Followings> following) {
        this.followings = following;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowingViewHolder holder, final int position) {
        HelperMedia.loadCirclePhoto(holder.followingUserName.getContext(), followings.get(position).getProfile_pic(), holder.followingUserImage);
        holder.followingUserName.setText(followings.get(position).getName());
        holder.followingUserNickName.setText(followings.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return followings.size();
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {

        CircleImageView followingUserImage;
        TextView followingUserName, followingUserNickName;


        FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }


        private void initUI(View view) {
            followingUserImage = view.findViewById(R.id.following_user_image);
            followingUserName = view.findViewById(R.id.following_user_name);
            followingUserNickName = view.findViewById(R.id.following_user_nick_name);
        }
    }
}
