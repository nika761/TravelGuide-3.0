package com.example.travelguide.ui.upload.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.response.MoodResponse;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;

import java.util.List;

public class MoodsAdapter extends RecyclerView.Adapter<MoodsAdapter.MoodsHolder> {

    private List<MoodResponse.Moods> moods;
    private ISearchMusic iSearchMusic;
    private int currentPosition = -1;

    public MoodsAdapter(List<MoodResponse.Moods> moods, ISearchMusic iSearchMusic) {
        this.moods = moods;
        this.iSearchMusic = iSearchMusic;
    }

    @NonNull
    @Override
    public MoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoodsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoodsHolder holder, int position) {
        if (currentPosition == position) {
            holder.moodTitle.setTextColor(holder.moodTitle.getResources().getColor(R.color.yellowTextView, null));
        }else {
            holder.moodTitle.setTextColor(holder.moodTitle.getResources().getColor(R.color.blackStatusBar, null));
        }
        holder.moodTitle.setText(moods.get(position).getMood());
    }

    @Override
    public int getItemCount() {
        return moods.size();
    }

    class MoodsHolder extends RecyclerView.ViewHolder {
        TextView moodTitle;

        MoodsHolder(@NonNull View itemView) {
            super(itemView);
            moodTitle = itemView.findViewById(R.id.item_moods_title);
            moodTitle.setOnClickListener(v -> {
                currentPosition = getLayoutPosition();
                iSearchMusic.onChooseMood(moods.get(getLayoutPosition()).getId());
                notifyDataSetChanged();
            });
        }
    }
}
