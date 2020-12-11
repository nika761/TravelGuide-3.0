package com.travel.guide.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private ArrayList<String> uris = new ArrayList<>();
    private HashMap<Integer, Integer> selectedItemPositions = new HashMap<>();
    private Context context;
    private boolean isImage;
    private GalleryFragment.ItemCountChangeListener listener;

    private int itemWidth;

    GalleryAdapter(Context context, boolean isImage) {
        this.isImage = isImage;
        this.context = context;
        listener = (GalleryFragment.ItemCountChangeListener) context;
    }

    @NonNull
    @Override
    public GalleryAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ImageViewHolder holder, int position) {

        if (isImage) {
//            holder.duration.setVisibility(View.GONE);
            holder.imageView.getLayoutParams().width = itemWidth;
            HelperMedia.loadPhoto(context, uris.get(position), holder.imageView);
        } else {
            holder.imageView.getLayoutParams().width = itemWidth;
            HelperMedia.loadPhoto(context, uris.get(position), holder.imageView);
//            holder.duration.setVisibility(View.VISIBLE);
//            holder.duration.setText(HelperMedia.getVideoDuration(uris.get(position)));
        }

        if (selectedItemPositions.containsKey(position)) {
            holder.selectedItemCount.setBackground(holder.itemView.getContext().getDrawable(R.drawable.unselected_image_bg));
            holder.selectedItemCount.setText(String.valueOf(selectedItemPositions.get(position)));
        } else {
            holder.selectedItemCount.setText("");
            holder.selectedItemCount.setBackground(holder.itemView.getContext().getDrawable(R.drawable.selected_image_circle));
        }

    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    void setItems(ArrayList<String> uris) {
        this.uris = uris;
        notifyDataSetChanged();
    }

    void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView selectedItemCount, duration;
        View selectItems;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.media_photo);
            imageView.setOnClickListener(this);

            duration = itemView.findViewById(R.id.duration);

            selectedItemCount = itemView.findViewById(R.id.selected_image_item_cont);

            selectItems = itemView.findViewById(R.id.selected_image_item_count_view);
            selectItems.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selected_image_item_count_view:
                    if (selectedItemPositions.size() > 0) {
                        if (selectedItemPositions.containsKey(getLayoutPosition())) {
                            selectedItemPositions.remove(getLayoutPosition());
                            listener.onItemSelected(uris.get(getLayoutPosition()));
                        } else {
                            Toast.makeText(imageView.getContext(), "You can choose only one item", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedItemPositions.put(getLayoutPosition(), selectedItemPositions.size() + 1);
                        listener.onItemSelected(uris.get(getLayoutPosition()));
                    }
//                    if (selectedItemPositions.containsKey(getLayoutPosition())) {
//                        for (Map.Entry<Integer, Integer> integerIntegerEntry : selectedItemPositions.entrySet()) {
//                            if (selectedItemPositions.get(getLayoutPosition()) < (int) ((Map.Entry) integerIntegerEntry).getValue())
//                                selectedItemPositions.put((Integer) ((Map.Entry) integerIntegerEntry).getKey(), ((Integer) ((Map.Entry) integerIntegerEntry).getValue()) - 1);
//                        }
//                        selectedItemPositions.remove(getLayoutPosition());
//                    } else {
//                        selectedItemPositions.put(getLayoutPosition(), selectedItemPositions.size() + 1);
//                    }
                    notifyDataSetChanged();
//                    listener.selectedPaths(getSelectedPaths(), isImage);
//                    if (isImage) {
//                        listener.imageSelectedPaths(uris.get(getLayoutPosition()));
//                    } else {
//                        listener.videoSelectedPaths(uris.get(getLayoutPosition()));
//                    }
                    break;

                case R.id.media_photo:
                    Intent intent = new Intent(context, MediaDetailActivity.class);
                    intent.putExtra("is_image", isImage);
                    intent.putExtra("path", uris.get(getLayoutPosition()));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, imageView, "image_transition");
                    context.startActivity(intent, options.toBundle());
            }
        }
    }

    private ArrayList<String> getSelectedPaths() {
        ArrayList<String> selectedPaths = new ArrayList<>();

        for (Map.Entry<Integer, Integer> integerIntegerEntry : selectedItemPositions.entrySet()) {
            selectedPaths.add(uris.get((int) ((Map.Entry) integerIntegerEntry).getKey()));
        }
        return selectedPaths;
    }
}
