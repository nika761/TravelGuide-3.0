package com.example.travelguide.ui.home.profile.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;

public class FavoritePostAdapter extends RecyclerView.Adapter<FavoritePostAdapter.PostViewHolder> {
    private List<PostResponse.Posts> posts;
    private FavoritePostListener favoritePostListener;

    FavoritePostAdapter(List<PostResponse.Posts> posts, FavoritePostListener favoritePostListener) {
        this.posts = posts;
        this.favoritePostListener = favoritePostListener;
    }

    @NonNull
    @Override
    public FavoritePostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritePostAdapter.PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePostAdapter.PostViewHolder holder, int position) {
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getCover(), holder.postImage);
        holder.reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
        holder.nickName.setText(posts.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions;
        TextView nickName;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            reactions = itemView.findViewById(R.id.favorite_post_reactions);

            nickName = itemView.findViewById(R.id.item_customer_post_nick);

            postImage = itemView.findViewById(R.id.favorite_post_cover);
            postImage.setOnClickListener(v -> favoritePostListener.onPostChoose(posts.get(getLayoutPosition()).getPost_id()));
        }

    }
}
