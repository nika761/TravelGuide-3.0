package com.example.travelguide.upload.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.upload.interfaces.IUploadStory;

import java.util.ArrayList;

public class UploadStoryAdapter extends RecyclerView.Adapter<UploadStoryAdapter.UploadStoryViewHolder> {
    private Context context;
    private ArrayList<String> uriArrayList;
    private IUploadStory iUploadStory;

    public UploadStoryAdapter(Context context, IUploadStory iUploadStory) {
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
        if (uriArrayList.size() != 0) {
            if (uriArrayList.get(position) != null) {
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
        } else {
            Toast.makeText(context, "someError", Toast.LENGTH_SHORT).show();
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

    public void onCropFinish(String croppedPhoto, int adapterPosition) {
        uriArrayList.set(adapterPosition, croppedPhoto);
        notifyDataSetChanged();
    }

    public void onFilterFinish(String filteredPhoto, int adapterPosition) {
        uriArrayList.set(adapterPosition, filteredPhoto);
        notifyDataSetChanged();
    }


    class UploadStoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoItem;
        private VideoView videoItem;
        private ImageButton crop, filter;

        UploadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }

        private void initUI(View itemView) {
            crop = itemView.findViewById(R.id.crop_image);
            crop.setOnClickListener(this);
            filter = itemView.findViewById(R.id.filter_image);
            filter.setOnClickListener(this);
            photoItem = itemView.findViewById(R.id.photo_item);
            videoItem = itemView.findViewById(R.id.video_item);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.crop_image:
                    iUploadStory.onCropChoose(uriArrayList.get(getLayoutPosition()), getLayoutPosition());
                    break;
                case R.id.filter_image:
                    iUploadStory.onFilterChoose(uriArrayList.get(getLayoutPosition()), getLayoutPosition());
                    break;
            }
        }

//        private void setClickListeners() {
//            editPhotoItemBtn.setOnClickListener(v -> {
//                iUploadStory.onGetItem(uriArrayList.get(getLayoutPosition()),getLayoutPosition());
//            });
//        }

    }
}
