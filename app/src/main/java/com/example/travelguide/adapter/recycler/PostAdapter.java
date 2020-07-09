package com.example.travelguide.adapter.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.Post;
import com.genius.multiprogressbar.MultiProgressBar;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements MultiProgressBar.ProgressStepChangeListener {

    private Context context;
    private List<Bitmap> posts;
    private StoriesProgressView storiesProgressView;


//    public PostAdapter(Context context) {
//        this.context = context;
//    }

    public PostAdapter(List<Bitmap> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        this.storiesProgressView = holder.storiesProgressView;
        holder.imageView.setImageBitmap(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private void startStory(PostViewHolder holder, int position) {
        storiesProgressView.setStoriesCount(posts.size()); // <- set stories
        storiesProgressView.setStoryDuration(5000); // <- set a story duration
        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            @Override
            public void onNext() {
            }

            @Override
            public void onPrev() {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                storiesProgressView.destroy();
            }
        }); // <- set listener
        storiesProgressView.startStories();
    }

    public void setPosts(List<Bitmap> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public void onProgressStepChange(int i) {

    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        StoriesProgressView storiesProgressView;
        MultiProgressBar multiProgressBar;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_story);
            storiesProgressView = itemView.findViewById(R.id.stories);
            multiProgressBar = itemView.findViewById(R.id.mpb_main);
            multiProgressBar.start();
        }
    }
}
