package com.travel.guide.ui.home.profile.userFollowActivity.following;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.FollowingResponse;

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

        HelperMedia.loadCirclePhoto(holder.userName.getContext(), followings.get(position).getProfile_pic(), holder.userImage);
        holder.userName.setText(followings.get(position).getName());
        holder.userNickName.setText(followings.get(position).getNickname());


    }

    @Override
    public int getItemCount() {
        return followings.size();
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView userName, userNickName;


        FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.following_user_image);
            userName = itemView.findViewById(R.id.following_user_name);
            userNickName = itemView.findViewById(R.id.following_user_nick_name);
        }
    }
}
