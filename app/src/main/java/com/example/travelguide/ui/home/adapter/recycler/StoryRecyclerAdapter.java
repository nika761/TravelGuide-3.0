package com.example.travelguide.ui.home.adapter.recycler;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.ScaledVideoView;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponseModel;

import java.util.List;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.StoryHolder> {

    private List<PostResponseModel.Post_stories> stories;
    private OnLoadFinishListener onLoadFinishListener;
    private PostRecyclerAdapter.OnStoryListener onStoryListener;
    private boolean firstLoad = true;

    StoryRecyclerAdapter(PostRecyclerAdapter.OnStoryListener onStoryListener, OnLoadFinishListener onLoadFinishListener) {
        this.onStoryListener = onStoryListener;
        this.onLoadFinishListener = onLoadFinishListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder viewHolder, int position) {
//        if (firstLoad) {
        onLoadFinishListener.stopLoader();
        viewHolder.setVideoItem();
//            firstLoad = false;
//        } else {
//            viewHolder.onNextStory();
//        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    void setStories(List<PostResponseModel.Post_stories> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    int getStoryDuration(int position) {
        return stories.get(position).getSecond();
    }

    class StoryHolder extends RecyclerView.ViewHolder {

        View parent;
        VideoView videoItem;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            videoItem = itemView.findViewById(R.id.scalable_video);
        }

        void setVideoItem() {
            videoItem.setVideoURI(Uri.parse(stories.get(getLayoutPosition()).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
                mp.setLooping(true);
                videoItem.start();
                onStoryListener.onGetStories(getLayoutPosition(), stories.get(getLayoutPosition()).getSecond());
            });
        }

        void onNextStory() {
            videoItem.setVideoURI(Uri.parse(stories.get(getLayoutPosition()).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                videoItem.start();
                onStoryListener.onNextStory(getLayoutPosition(), stories.get(getLayoutPosition()).getSecond());
            });
        }
    }

}