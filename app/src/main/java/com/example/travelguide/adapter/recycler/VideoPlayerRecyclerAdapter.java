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

import com.bumptech.glide.RequestManager;
import com.example.travelguide.R;
import com.example.travelguide.model.Post;

import java.util.ArrayList;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mediaObjects;
    private RequestManager requestManager;


    public VideoPlayerRecyclerAdapter(ArrayList<String> mediaObjects, RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoPlayerViewHolder) viewHolder).onBind(mediaObjects, requestManager);
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
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

        void onBind(ArrayList<String> mediaObject, RequestManager requestManager) {
            this.requestManager = requestManager;
            parent.setTag(this);
//            title.setText(mediaObject.getTitle());
//            this.requestManager
//                    .load(mediaObject.getThumbnail())
//                    .into(thumbnail);
        }

    }

}