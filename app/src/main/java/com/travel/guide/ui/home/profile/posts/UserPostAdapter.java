package com.travel.guide.ui.home.profile.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.PostResponse;

import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostHolder> {
    private List<PostResponse.Posts> posts;
    private UserPostListener userPostListener;

    UserPostAdapter(UserPostListener userPostListener) {
        this.userPostListener = userPostListener;
    }

    @NonNull
    @Override
    public UserPostAdapter.UserPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserPostAdapter.UserPostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostAdapter.UserPostHolder holder, int position) {
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getCover(), holder.postImage);
        holder.reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
        holder.nickName.setText(String.valueOf(posts.get(position).getNickname()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        if (this.posts != null && this.posts.size() != 0)
            this.posts.addAll(posts);

        else {
            this.posts = posts;
            notifyDataSetChanged();
        }
    }

    class UserPostHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions, nickName;

        UserPostHolder(@NonNull View itemView) {
            super(itemView);

            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            postImage.setOnClickListener(v -> userPostListener.onPostChoose(posts.get(getLayoutPosition()).getPost_id()));
        }
    }
}
