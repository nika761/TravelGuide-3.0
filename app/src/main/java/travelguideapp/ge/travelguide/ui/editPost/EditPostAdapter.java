package travelguideapp.ge.travelguide.ui.editPost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.customModel.ItemMedia;

import java.util.List;

public class EditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemMedia> itemMedias;
    private EditPostCallback editPostCallback;

    EditPostAdapter(EditPostCallback editPostCallback) {
        this.editPostCallback = editPostCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new PhotoItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_story_photo, parent, false));
        else
            return new VideoItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_story_video, parent, false));

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

    void setItemMedias(List<ItemMedia> itemMedias) {
        this.itemMedias = itemMedias;
        notifyDataSetChanged();
    }

    void onCropFinish(String croppedPhoto, int itemPosition) {
        itemMedias.set(itemPosition, new ItemMedia(0, croppedPhoto));
        notifyDataSetChanged();
    }

    void onFilterFinish(String filteredPhoto, int itemPosition) {
        itemMedias.set(itemPosition, new ItemMedia(0, filteredPhoto));
        notifyDataSetChanged();
    }

    void onTrimFinish(String trimmedVideo, int itemPosition) {
        itemMedias.set(itemPosition, new ItemMedia(1, trimmedVideo));
        notifyDataSetChanged();
    }

    class PhotoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoItem;

        PhotoItemHolder(@NonNull View itemView) {
            super(itemView);

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
                    editPostCallback.onCropChoose(itemMedias.get(getLayoutPosition()).getPath(), getLayoutPosition());
                    break;

                case R.id.filter_image:
                    editPostCallback.onFilterChoose(itemMedias.get(getLayoutPosition()).getPath(), getLayoutPosition());
                    break;

                case R.id.sort_image:
                    editPostCallback.onSortChoose(itemMedias);
                    break;

                case R.id.delete_image:
                    itemMedias.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    editPostCallback.onStoryDeleted(itemMedias);
                    break;
            }
        }

        private void setPhotoItem(int position) {
            HelperMedia.loadPhoto(photoItem.getContext(), itemMedias.get(position).getPath(), photoItem);
        }
    }

    class VideoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private VideoView videoItem;
        private LinearLayout speedTools;
        private boolean visibility;

        VideoItemHolder(@NonNull View itemView) {
            super(itemView);

            ImageButton delete = itemView.findViewById(R.id.delete_image_video);
            delete.setOnClickListener(this);

            ImageButton sort = itemView.findViewById(R.id.sort_video);
            sort.setOnClickListener(this);

//            ImageButton speed = itemView.findViewById(R.id.speed_video);
//            speed.setOnClickListener(this);

            ImageButton trim = itemView.findViewById(R.id.trim_video);
            trim.setOnClickListener(this);

            videoItem = itemView.findViewById(R.id.video_item);
            videoItem.setOnClickListener(this);

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
                    editPostCallback.onTrimChoose(itemMedias.get(getLayoutPosition()).getPath(), getLayoutPosition());
                    break;
//                case R.id.speed_video:
//                    if (visibility) {
//                        speedTools.setVisibility(View.GONE);
//                        visibility = false;
//                    } else {
//                        speedTools.setVisibility(View.VISIBLE);
//                        visibility = true;
//                    }
//                    break;
                case R.id.sort_video:
                    editPostCallback.onSortChoose(itemMedias);
                    break;

                case R.id.video_item:
                    if (videoItem.isPlaying()) {
                        videoItem.pause();
                    } else {
                        videoItem.resume();
                    }
                    break;

                case R.id.delete_image_video:
                    itemMedias.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    editPostCallback.onStoryDeleted(itemMedias);
                    break;
            }
        }
    }

}
