package com.example.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.FollowerResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder> {

    private List<FollowerResponse.Followers> followers;
    private TagPostListener tagPostListener;

    FriendsAdapter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
    }

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public void setFollowers(List<FollowerResponse.Followers> followers) {
        this.followers = followers;
        notifyDataSetChanged();
    }

    class FriendsHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImage;
        ConstraintLayout container;
        TextView userName;
        TextView nickName;
        TextView follow;

        FriendsHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.followers_container);
            container.setOnClickListener(v -> tagPostListener.onChooseFollower(followers.get(getLayoutPosition()).getUser_id(), followers.get(getLayoutPosition()).getName()));

            profileImage = itemView.findViewById(R.id.followers_user_image);
            userName = itemView.findViewById(R.id.followers_user_name);
            nickName = itemView.findViewById(R.id.followers_user_nick_name);
            follow = itemView.findViewById(R.id.followers_user_follow);
            follow.setVisibility(View.GONE);
        }

        void bindView(int position) {
            HelperMedia.loadCirclePhoto(follow.getContext(), followers.get(position).getProfile_pic(), profileImage);
            userName.setText(followers.get(position).getName());
            nickName.setText(followers.get(position).getNickname());
        }
    }
}
