package com.example.travelguide.home.adapter.recycler;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.travelguide.R;
import com.example.travelguide.helper.ScaledVideoView;
import com.example.travelguide.home.interfaces.OnStoryChangeListener;
import com.example.travelguide.model.response.PostResponseModel;

import java.util.List;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.StoryHolder> {

    private List<PostResponseModel.Post_stories> stories;
    private OnStoryChangeListener onStoryChangeListener;

    StoryRecyclerAdapter(OnStoryChangeListener onStoryChangeListener) {
        this.onStoryChangeListener = onStoryChangeListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder viewHolder, int position) {
        onStoryChangeListener.onStorySelected(position);
        viewHolder.videoItem.setVideoURI(Uri.parse(stories.get(position).getUrl()));// use methods to set url
        viewHolder.videoItem.requestFocus();
        viewHolder.videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
            mp.setLooping(true);
            viewHolder.videoItem.start();
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    void setStories(List<PostResponseModel.Post_stories> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    class StoryHolder extends RecyclerView.ViewHolder {

        View parent;
        ScaledVideoView videoItem;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            videoItem = itemView.findViewById(R.id.scalable_video);
        }
    }

}