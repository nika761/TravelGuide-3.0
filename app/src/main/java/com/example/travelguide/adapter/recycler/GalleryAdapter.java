package com.example.travelguide.adapter.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.activity.DetailActivity;
import com.example.travelguide.fragments.MediaFragment;
import com.example.travelguide.utils.UtilsMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {


    private ArrayList<String> uris = new ArrayList<>();
    private HashMap<Integer, Integer> selecedImagePositions = new HashMap<>();
    private Context context;
    private boolean isImage;
    private MediaFragment.ItemCountChangeListener listener;

    public GalleryAdapter(Context context, boolean isImage) {
        this.isImage = isImage;
        this.context = context;
        listener = (MediaFragment.ItemCountChangeListener) context;
    }

    @NonNull
    @Override
    public GalleryAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ImageViewHolder holder, int position) {
        if (isImage) {
            holder.duration.setVisibility(View.GONE);
            Glide.with(holder.imageView.getContext())
                    .load(uris.get(position))
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.imageView);
        } else {
            Glide.with(holder.imageView.getContext())
                    .load(uris.get(position))
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.imageView);
            holder.duration.setVisibility(View.VISIBLE);
            holder.duration.setText(UtilsMedia.setVideoDuration(uris.get(position)));
        }

        if (selecedImagePositions.containsKey(position)) {
            holder.selectedItemCount.setBackground(holder.itemView.getContext().getDrawable(R.drawable.unselected_image_bg));
            holder.selectedItemCount.setText(String.valueOf(selecedImagePositions.get(position)));
        } else {
            holder.selectedItemCount.setText("");
            holder.selectedItemCount.setBackground(holder.itemView.getContext().getDrawable(R.drawable.selected_image_circle));
        }
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public void setItems(ArrayList<String> uris) {
        this.uris = uris;
        notifyDataSetChanged();
    }

    public ArrayList<String> getPickedItems() {
        ArrayList<String> pickedItems = new ArrayList<>();
        if (selecedImagePositions.size() <= 10) {
            for (int i = 0; i < selecedImagePositions.size(); i++) {
                pickedItems.add(uris.get(i));
            }
        }
        return pickedItems;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView selectedItemCount, duration;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_photo);
            duration = itemView.findViewById(R.id.duration);
            selectedItemCount = itemView.findViewById(R.id.selected_image_item_cont);
            imageView.setOnClickListener(this);
            selectedItemCount.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selected_image_item_cont:
                    if (selecedImagePositions.containsKey(getLayoutPosition())) {
                        Iterator it = selecedImagePositions.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            if (selecedImagePositions.get(getLayoutPosition()) < (int) pair.getValue())
                                selecedImagePositions.put((Integer) pair.getKey(), ((Integer) pair.getValue()) - 1);
                        }
                        selecedImagePositions.remove(getLayoutPosition());

                    } else {
                        selecedImagePositions.put(getLayoutPosition(), selecedImagePositions.size() + 1);
                    }
                    notifyDataSetChanged();
                    if (isImage)
                        listener.imageSelectedPaths(getSelectedPaths());
                    else
                        listener.videoSelectedPaths(getSelectedPaths());
                    break;

                case R.id.media_photo:
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("is_image", isImage);
                    intent.putExtra("path", uris.get(getLayoutPosition()));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, imageView, "image_transition");
                    context.startActivity(intent, options.toBundle());
            }
        }
    }

    private ArrayList<String> getSelectedPaths() {
        ArrayList<String> selectedPaths = new ArrayList<>();

        Iterator it = selecedImagePositions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            selectedPaths.add(uris.get((int) pair.getKey()));
        }
        return selectedPaths;
    }
}
