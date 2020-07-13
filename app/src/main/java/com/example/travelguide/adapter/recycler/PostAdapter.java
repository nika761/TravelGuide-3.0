package com.example.travelguide.adapter.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.utils.UtilsMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private ArrayList<String> posts;

//    public PostAdapter(Context context) {
//        this.context = context;
//    }

    public PostAdapter(ArrayList<String> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.videoView.setVisibility(View.VISIBLE);
        holder.videoView.setVideoPath(posts.get(position));
        holder.storiesProgressView.setStoriesCount(1);
        holder.storiesProgressView.setStoryDuration(UtilsMedia.getVideoDurationInt(posts.get(position)));
        holder.videoView.start();
        holder.storiesProgressView.startStories();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        StoriesProgressView storiesProgressView;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.image_story);
            storiesProgressView = itemView.findViewById(R.id.stories);
        }
    }
}
