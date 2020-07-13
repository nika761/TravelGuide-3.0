package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.interfaces.IUploadStory;

import java.util.ArrayList;

public class PrepareStoryAdapter extends RecyclerView.Adapter<PrepareStoryAdapter.UploadStoryViewHolder> {
    private Context context;
    private ArrayList<String> uriArrayList;
    private IUploadStory iUploadStory;

    public PrepareStoryAdapter(Context context, IUploadStory iUploadStory) {
        this.context = context;
        this.iUploadStory = iUploadStory;
    }

    @NonNull
    @Override
    public UploadStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prepare_story, parent, false);
        return new UploadStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadStoryViewHolder holder, int position) {
        if (uriArrayList.get(position).endsWith(".mp4")) {
            holder.photoItem.setVisibility(View.INVISIBLE);
            holder.videoItem.setVisibility(View.VISIBLE);
            holder.videoItem.setVideoPath(uriArrayList.get(position));
//            videoView.setVideoURI(video);
            holder.videoItem.requestFocus();
            holder.videoItem.setOnPreparedListener(mp -> {
                holder.videoItem.setMediaController(new MediaController(context));
                mp.setLooping(true);
                holder.videoItem.start();
            });
        } else {
            holder.photoItem.setVisibility(View.VISIBLE);
            holder.videoItem.setVisibility(View.INVISIBLE);
            Glide.with(holder.photoItem.getContext())
                    .load(uriArrayList.get(position))
                    .apply(new RequestOptions().centerInside())
                    .into(holder.photoItem);
//        holder.photoItem.setImageURI(uriArrayList.get(position));
//        holder.editPhotoItemBtn.setOnClickListener(v -> iUploadStory.onGetItem(uriArrayList.get(position), position));
        }
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }


    public void setUriArrayList(ArrayList<String> uriArrayList) {
        this.uriArrayList = uriArrayList;
        notifyDataSetChanged();
    }

    public void onCropResult(String croppedPhoto, int adapterPosition) {
        uriArrayList.set(adapterPosition, croppedPhoto);
        notifyDataSetChanged();
    }


    class UploadStoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoItem;
        private Button editPhotoItemBtn;
        private VideoView videoItem;

        UploadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }

        private void initUI(View itemView) {
            photoItem = itemView.findViewById(R.id.photo_item);
            editPhotoItemBtn = itemView.findViewById(R.id.edit_photo_item_btn);
            videoItem = itemView.findViewById(R.id.video_item);
        }

//        private void setClickListeners() {
//            editPhotoItemBtn.setOnClickListener(v -> {
//                iUploadStory.onGetItem(uriArrayList.get(getLayoutPosition()),getLayoutPosition());
//            });
//        }

    }
}
