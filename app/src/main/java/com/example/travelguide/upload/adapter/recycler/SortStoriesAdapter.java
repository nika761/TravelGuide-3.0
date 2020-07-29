package com.example.travelguide.upload.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperGlide;
import com.example.travelguide.upload.interfaces.ISortListener;

import java.util.ArrayList;

public class SortStoriesAdapter extends RecyclerView.Adapter<SortStoriesAdapter.SortStoriesHolder> {

    private ArrayList<String> stories;

    public SortStoriesAdapter(ArrayList<String> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public SortStoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_stories, parent, false);
        return new SortStoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortStoriesHolder holder, int position) {
        holder.sortPosition.setText(String.valueOf(position + 1));
        HelperGlide.loadPhoto(holder.imageView.getContext(), stories.get(position), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class SortStoriesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView sortPosition;

        SortStoriesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sort_item_image);
            sortPosition = itemView.findViewById(R.id.sort_item_position);
        }
    }
}
