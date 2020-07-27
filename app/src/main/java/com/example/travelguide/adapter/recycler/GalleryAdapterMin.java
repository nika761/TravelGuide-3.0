package com.example.travelguide.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.fragments.GalleryFragment;
import com.example.travelguide.helper.HelperMedia;

import java.util.ArrayList;

public class GalleryAdapterMin extends RecyclerView.Adapter<GalleryAdapterMin.ViewHolder> {

    private ArrayList<String> selectedItemsPath = new ArrayList<>();
    private GalleryFragment.ItemCountChangeListener listener;

    public GalleryAdapterMin(GalleryFragment.ItemCountChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_picker_min, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (selectedItemsPath.get(position).endsWith(".mp4")) {
            Glide.with(holder.imageView.getContext())
                    .load(selectedItemsPath.get(position))
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.imageView);
            holder.duration.setVisibility(View.VISIBLE);
            holder.duration.setText(HelperMedia.getVideoDuration(selectedItemsPath.get(position)));
        } else {
            holder.duration.setVisibility(View.GONE);
            Glide.with(holder.imageView.getContext())
                    .load(selectedItemsPath.get(position))
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return selectedItemsPath.size();
    }

    public void setItemsPath(ArrayList<String> selectedItemsPath) {
        this.selectedItemsPath = selectedItemsPath;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private ImageButton imageButton;
        private TextView duration;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_photo_min);
            duration = itemView.findViewById(R.id.duration_min);
            imageView.setOnClickListener(this);
            imageButton = itemView.findViewById(R.id.cut_media_photo_min);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.media_photo_min:
                    break;
                case R.id.cut_media_photo_min:
                    selectedItemsPath.remove(getLayoutPosition());
                    notifyDataSetChanged();
                    listener.onItemRemoved(selectedItemsPath);
                    break;
            }
        }
    }
}
