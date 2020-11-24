package com.travel.guide.ui.home.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.enums.SearchPostType;
import com.travel.guide.ui.searchPost.SearchPostActivity;

import java.util.List;


public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagHolder> {
    private List<String> hashtags;

    public HashtagAdapter(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        holder.hashtag.setText(hashtags.get(position));
    }

    @Override
    public int getItemCount() {
        return hashtags.size();
    }

    class HashtagHolder extends RecyclerView.ViewHolder {
        TextView hashtag;

        HashtagHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtag_hashtag);
            hashtag.setOnClickListener(v -> {
                Intent postHashtagIntent = new Intent(hashtag.getContext(), SearchPostActivity.class);
                postHashtagIntent.putExtra("search_type", SearchPostType.HASHTAG);
                postHashtagIntent.putExtra("search_hashtag", hashtags.get(getLayoutPosition()));
                hashtag.getContext().startActivity(postHashtagIntent);
            });
        }

    }
}
