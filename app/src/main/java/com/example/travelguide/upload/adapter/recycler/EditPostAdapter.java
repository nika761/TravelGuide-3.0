package com.example.travelguide.upload.adapter.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.helper.ScaledVideoView;
import com.example.travelguide.upload.activity.GalleryPickerActivity;
import com.example.travelguide.upload.interfaces.IUploadStory;

import java.util.ArrayList;

public class EditPostAdapter extends RecyclerView.Adapter<EditPostAdapter.UploadStoryViewHolder> {
    private Context context;
    private ArrayList<String> itemPaths;
    private IUploadStory iUploadStory;

    public EditPostAdapter(Context context, IUploadStory iUploadStory) {
        this.context = context;
        this.iUploadStory = iUploadStory;
    }

    @NonNull
    @Override
    public UploadStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_post, parent, false);
        return new UploadStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadStoryViewHolder holder, int position) {
        if (itemPaths.size() != 0) {
            if (itemPaths.get(position) != null) {
                if (itemPaths.get(position).endsWith(".mp4")) {
                    holder.photoItem.setVisibility(View.GONE);
                    holder.videoItem.setVisibility(View.VISIBLE);
                    holder.videoItem.setVideoPath(itemPaths.get(position));
//            videoView.setVideoURI(video);
                    holder.videoItem.requestFocus();
                    holder.videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
                        mp.setLooping(true);
                        holder.videoItem.start();
                    });
                } else {
                    holder.photoItem.setVisibility(View.VISIBLE);
                    holder.videoItem.setVisibility(View.GONE);
                    Glide.with(holder.photoItem.getContext())
                            .load(itemPaths.get(position))
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
        return itemPaths.size();
    }

    public void setUriArrayList(ArrayList<String> uriArrayList) {
        this.itemPaths = uriArrayList;
        notifyDataSetChanged();
    }

    public void onCropFinish(String croppedPhoto, int adapterPosition) {
        itemPaths.set(adapterPosition, croppedPhoto);
        notifyDataSetChanged();
    }

    public void onFilterFinish(String filteredPhoto, int adapterPosition) {
        itemPaths.set(adapterPosition, filteredPhoto);
        notifyDataSetChanged();
    }


    class UploadStoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoItem;
        private ScaledVideoView videoItem;

        UploadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }

        private void initUI(View itemView) {
            ImageButton crop = itemView.findViewById(R.id.crop_image);
            crop.setOnClickListener(this);

            ImageButton filter = itemView.findViewById(R.id.filter_image);
            filter.setOnClickListener(this);

            ImageButton delete = itemView.findViewById(R.id.delete_image);
            delete.setOnClickListener(this);

            photoItem = itemView.findViewById(R.id.photo_item);
            videoItem = itemView.findViewById(R.id.video_item);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.crop_image:
                    iUploadStory.onCropChoose(itemPaths.get(getLayoutPosition()), getLayoutPosition());
                    break;
                case R.id.filter_image:
                    iUploadStory.onFilterChoose(itemPaths.get(getLayoutPosition()), getLayoutPosition());
                    break;
                case R.id.delete_image:
                    itemPaths.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    if (itemPaths.size() == 0) {
                        Intent intent = new Intent(context, GalleryPickerActivity.class);
                        context.startActivity(intent);
                    }
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
