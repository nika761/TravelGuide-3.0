package com.example.travelguide.ui.upload.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.SearchMusicHolder> {
    @NonNull
    @Override
    public SearchMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMusicHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SearchMusicHolder extends RecyclerView.ViewHolder {
        public SearchMusicHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
