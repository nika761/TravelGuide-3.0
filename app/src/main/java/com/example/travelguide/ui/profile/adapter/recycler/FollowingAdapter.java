package com.example.travelguide.ui.profile.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private Context context;
    private List<User> users;

    public FollowingAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following_recycler, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowingViewHolder holder, final int position) {
        final User currentUser = users.get(position);

        setDataIntoViews(holder, currentUser);
    }

    private void setDataIntoViews(FollowingViewHolder viewHolder, User currentUser) {
        HelperMedia.loadCirclePhoto(context, currentUser.getUrl(), viewHolder.followingUserImage);
        viewHolder.followingUserName.setText(currentUser.getName());
        viewHolder.followingUserLastName.setText(currentUser.getLastName());
        viewHolder.followingUserNickName.setText(currentUser.getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {

        CircleImageView followingUserImage;
        TextView followingUserName, followingUserLastName, followingUserNickName;


        FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            iniUi(itemView);
        }


        private void iniUi(View view) {
            followingUserImage = view.findViewById(R.id.following_user_image);
            followingUserName = view.findViewById(R.id.following_user_name);
            followingUserLastName = view.findViewById(R.id.following_user_last_name);
            followingUserNickName = view.findViewById(R.id.following_user_nick_name);

        }
    }
}
