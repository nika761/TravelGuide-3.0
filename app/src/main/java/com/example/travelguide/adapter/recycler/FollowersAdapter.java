package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.UtilsGlide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {
    private Context context;
    private List<User> users;

    public FollowersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers_recycler, parent, false);
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        final User currentUser = users.get(position);

        setDataIntoViews(holder, currentUser);
    }

    private void setDataIntoViews(FollowersViewHolder viewHolder, User currentUser) {
        UtilsGlide.loadCirclePhoto(context, currentUser.getUrl(), viewHolder.followersUserImage);
        viewHolder.followersUserName.setText(currentUser.getName());
        viewHolder.followersUserLastName.setText(currentUser.getLastName());
        viewHolder.followersUserNickName.setText(currentUser.getEmail());

    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    class FollowersViewHolder extends RecyclerView.ViewHolder {

        CircleImageView followersUserImage;
        TextView followersUserName, followersUserLastName, followersUserNickName;


        FollowersViewHolder(@NonNull View itemView) {
            super(itemView);
            iniUi(itemView);
        }


        private void iniUi(View view) {
            followersUserImage = view.findViewById(R.id.followers_user_image);
            followersUserName = view.findViewById(R.id.followers_user_name);
            followersUserLastName = view.findViewById(R.id.followers_user_last_name);
            followersUserNickName = view.findViewById(R.id.followers_user_nick_name);
        }
    }


}
