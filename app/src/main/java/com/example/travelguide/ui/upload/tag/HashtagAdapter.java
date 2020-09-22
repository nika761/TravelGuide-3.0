package com.example.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.response.FollowerResponse;
import com.example.travelguide.model.response.HashtagResponse;

import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagHolder> {

    private List<HashtagResponse.Hashtags> hashtags;
    private TagPostListener tagPostListener;

    public HashtagAdapter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        holder.hashtag.setText(hashtags.get(position).getHashtag());
    }

    @Override
    public int getItemCount() {
        return hashtags.size();
    }

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        this.hashtags = hashtags;
        notifyDataSetChanged();
    }

    class HashtagHolder extends RecyclerView.ViewHolder {

        TextView hashtag;

        HashtagHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtag_search);
            hashtag.setOnClickListener(v -> tagPostListener.onChooseHashtag(hashtags.get(getLayoutPosition()).getHashtag()));
        }

    }
}
