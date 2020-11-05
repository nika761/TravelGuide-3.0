package com.travel.guide.ui.upload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;

import java.util.ArrayList;

public class UploadPostAdapter extends RecyclerView.Adapter<UploadPostAdapter.DescribeStoriesHolder> {
    private ArrayList<String> storiesPaths;

    public UploadPostAdapter(ArrayList<String> storiesPaths) {
        this.storiesPaths = storiesPaths;
    }

    @NonNull
    @Override
    public DescribeStoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_describe_post, parent, false);
        return new DescribeStoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DescribeStoriesHolder holder, int position) {
        HelperMedia.loadPhoto(holder.imageView.getContext(), storiesPaths.get(position), holder.imageView);
        holder.textView.setText("Story " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return storiesPaths.size();
    }

    class DescribeStoriesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        DescribeStoriesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.describe_story_photo);
            textView = itemView.findViewById(R.id.describe_story_counter);
        }
    }
}
