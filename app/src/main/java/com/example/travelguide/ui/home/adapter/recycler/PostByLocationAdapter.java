package com.example.travelguide.ui.home.adapter.recycler;

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

public class PostByLocationAdapter extends RecyclerView.Adapter<PostByLocationAdapter.PostLocationHolder> {
    private List<PostResponse.Posts> posts;

    public PostByLocationAdapter(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostLocationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostLocationHolder holder, int position) {
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getCover(), holder.postImage);
        holder.nickName.setText(posts.get(position).getNickname());
        holder.reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostLocationHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView nickName, reactions;

        PostLocationHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.item_post_list_image);
            reactions = itemView.findViewById(R.id.item_post_list_states);
            nickName = itemView.findViewById(R.id.item_post_list_nickname);
        }
    }
}
