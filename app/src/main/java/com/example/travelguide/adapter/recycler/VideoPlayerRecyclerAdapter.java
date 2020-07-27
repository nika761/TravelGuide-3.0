package com.example.travelguide.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.RequestManager;
import com.example.travelguide.R;
import com.example.travelguide.model.Post;
import com.example.travelguide.model.response.PostResponseModel;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PostResponseModel.Post_stories> stories;
    private RequestManager requestManager;


    VideoPlayerRecyclerAdapter(List<PostResponseModel.Post_stories> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoPlayerViewHolder) viewHolder).onBind(stories);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

        FrameLayout media_container;
        //        TextView title;
        ImageView thumbnail, volumeControl;
        ProgressBar progressBar;
        View parent;
        RequestManager requestManager;

        VideoPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
//            media_container = itemView.findViewById(R.id.media_container);
//            thumbnail = itemView.findViewById(R.id.thumbnail);
//            title = itemView.findViewById(R.id.title);
//            progressBar = itemView.findViewById(R.id.progressBar);
//            volumeControl = itemView.findViewById(R.id.volume_control);
        }

        void onBind(List<PostResponseModel.Post_stories> stories) {
            parent.setTag(this);
//            title.setText(mediaObject.getTitle());
//            this.requestManager
//                    .load(mediaObject.getThumbnail())
//                    .into(thumbnail);
        }

    }

}