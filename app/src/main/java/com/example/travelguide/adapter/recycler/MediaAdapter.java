package com.example.travelguide.adapter.recycler;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ImageViewHolder> {

    private ArrayList<String> uris = new ArrayList<>();
    private HashMap<Integer, Integer> selecedImagePositions = new HashMap<>();

    @NonNull
    @Override
    public MediaAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MediaAdapter.ImageViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
                .load(uris.get(position))
                .apply(new RequestOptions().centerCrop())
                .into(holder.imageView);

        if (selecedImagePositions.containsKey(position)) {
            holder.selectedItemCount.setBackground(holder.itemView.getContext().getDrawable(R.drawable.unselected_image_bg));
            holder.selectedItemCount.setText(String.valueOf(selecedImagePositions.get(position)));
        } else {
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

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView selectedItemCount;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_photo);
            selectedItemCount = itemView.findViewById(R.id.selected_image_item_cont);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
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
        }
    }
}
