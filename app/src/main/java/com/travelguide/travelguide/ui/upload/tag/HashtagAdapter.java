package com.travelguide.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.model.response.HashtagResponse;

import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagHolder> {

    private List<HashtagResponse.Hashtags> hashtags;
    private TagPostListener tagPostListener;
    private List<String> hashs;
    private int type;

    public HashtagAdapter(int type) {
        this.type = type;
    }

    public HashtagAdapter(TagPostListener tagPostListener, int type) {
        this.type = type;
        this.tagPostListener = tagPostListener;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        if (type == 0) {
            holder.hashtag.setText(hashtags.get(position).getHashtag());
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.hashtag.setText(hashs.get(position));
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0)
            return hashtags.size();
        else
            return hashs.size();
    }

    public void setHashs(List<String> hashs) {
        this.hashs = hashs;
        notifyDataSetChanged();
    }

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        this.hashtags = hashtags;
        notifyDataSetChanged();
    }

    class HashtagHolder extends RecyclerView.ViewHolder {

        TextView hashtag;
        ImageButton delete;

        HashtagHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.item_search_hashtag_delete);
            hashtag = itemView.findViewById(R.id.hashtag_search);

            if (type == 0)
                hashtag.setOnClickListener(v -> tagPostListener.onChooseHashtag(hashtags.get(getLayoutPosition()).getHashtag()));
            else
                delete.setOnClickListener(v -> {
                    hashs.remove(getLayoutPosition());
                    notifyDataSetChanged();
                });
        }

    }
}
