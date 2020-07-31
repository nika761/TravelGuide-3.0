package com.example.travelguide.ui.upload.adapter.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.ScaledVideoView;
import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.ui.upload.activity.GalleryPickerActivity;
import com.example.travelguide.ui.upload.interfaces.IEditPost;

import java.util.List;

public class EditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ItemMedia> itemMedias;
    private IEditPost iEditPost;

    public EditPostAdapter(Context context, IEditPost iEditPost) {
        this.context = context;
        this.iEditPost = iEditPost;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new VideoItemHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_edit_story_video, parent, false));
        }
        return new PhotoItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit_story_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
                ((PhotoItemHolder) holder).setPhotoItem(position);
                break;
            case 1:
                ((VideoItemHolder) holder).setVideoItem(position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemMedias.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return itemMedias.size();
    }

    public void setItemMedias(List<ItemMedia> itemMedias) {
        this.itemMedias = itemMedias;
        notifyDataSetChanged();
    }

    public void onCropFinish(String croppedPhoto, int adapterPosition) {
        itemMedias.set(adapterPosition, new ItemMedia(0, croppedPhoto));
        notifyDataSetChanged();
    }

    public void onFilterFinish(String filteredPhoto, int adapterPosition) {
        itemMedias.set(adapterPosition, new ItemMedia(0, filteredPhoto));
        notifyDataSetChanged();
    }


    class PhotoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoItem;

        PhotoItemHolder(@NonNull View itemView) {
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

            ImageButton sort = itemView.findViewById(R.id.sort_image);
            sort.setOnClickListener(this);

            photoItem = itemView.findViewById(R.id.photo_item);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.crop_image:
                    iEditPost.onCropChoose(itemMedias.get(getLayoutPosition()).getPath(), getLayoutPosition());
                    break;

                case R.id.filter_image:
                    iEditPost.onFilterChoose(itemMedias.get(getLayoutPosition()).getPath(), getLayoutPosition());
                    break;

                case R.id.sort_image:
                    iEditPost.onSortChoose(itemMedias);
                    break;

                case R.id.delete_image:
                    itemMedias.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    iEditPost.onStoryDeleted(itemMedias);

                    if (itemMedias.size() == 0) {
                        Intent intent = new Intent(context, GalleryPickerActivity.class);
                        context.startActivity(intent);
                    }
                    break;
            }
        }

        private void setPhotoItem(int position) {
            HelperMedia.loadPhoto(photoItem.getContext(), itemMedias.get(position).getPath(), photoItem);
        }
    }

    class VideoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ScaledVideoView videoItem;
        private LinearLayout speedTools;
        private boolean visibility;

        VideoItemHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }

        private void initUI(View itemView) {

            ImageButton delete = itemView.findViewById(R.id.delete_image_video);
            delete.setOnClickListener(this);

            ImageButton sort = itemView.findViewById(R.id.sort_video);
            sort.setOnClickListener(this);

            ImageButton speed = itemView.findViewById(R.id.speed_video);
            speed.setOnClickListener(this);

            ImageButton trim = itemView.findViewById(R.id.trim_video);
            trim.setOnClickListener(this);

            videoItem = itemView.findViewById(R.id.video_item);
            speedTools = itemView.findViewById(R.id.speed_container);
        }

        private void setVideoItem(int position) {
            videoItem.setVideoPath(itemMedias.get(position).getPath());
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
                mp.setLooping(true);
                videoItem.start();
            });
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.trim_video:
                    iEditPost.onTrimChoose(itemMedias.get(getLayoutPosition()).getPath());
                    break;

                case R.id.speed_video:
                    if (visibility) {
                        speedTools.setVisibility(View.GONE);
                        visibility = false;
                    } else {
                        speedTools.setVisibility(View.VISIBLE);
                        visibility = true;
                    }
                    break;

                case R.id.sort_video:
                    iEditPost.onSortChoose(itemMedias);
                    break;

                case R.id.delete_image_video:
                    itemMedias.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    iEditPost.onStoryDeleted(itemMedias);

                    if (itemMedias.size() == 0) {
                        Intent intent = new Intent(context, GalleryPickerActivity.class);
                        context.startActivity(intent);
                    }
                    break;
            }
        }
    }
}
