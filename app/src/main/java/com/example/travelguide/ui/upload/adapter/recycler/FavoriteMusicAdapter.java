package com.example.travelguide.ui.upload.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.FavoriteMusicResponse;

import java.util.List;

public class FavoriteMusicAdapter extends RecyclerView.Adapter<FavoriteMusicAdapter.FavoriteMusicHolder> {
    private List<FavoriteMusicResponse.Favotite_musics> favoriteMusics;
    private int playingPosition = -1;

    public FavoriteMusicAdapter(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics) {
        this.favoriteMusics = favoriteMusics;
    }

    @NonNull
    @Override
    public FavoriteMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteMusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMusicHolder holder, int position) {
        holder.musicTitle.setText(favoriteMusics.get(position).getTitle());
        holder.musicTitle.setSelected(true);
        holder.musicAuthor.setText(favoriteMusics.get(position).getAuthor());
        holder.musicAuthor.setSelected(true);
        holder.musicDuration.setText(favoriteMusics.get(position).getDuration());
        if (playingPosition == position) {
            holder.playBtn.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
        } else {
            holder.playBtn.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
        }
        if (favoriteMusics.get(position).getCategories().size() != 0) {
            holder.musicCategory.setText(favoriteMusics.get(position).getCategories().get(0).getCategory());
        }
        HelperMedia.loadPhoto(holder.musicImage.getContext(), favoriteMusics.get(position).getImage(), holder.musicImage);
        holder.favorite.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
    }

    @Override
    public int getItemCount() {
        return favoriteMusics.size();
    }

    class FavoriteMusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView musicTitle, musicAuthor, musicCategory, musicDuration;
        ImageView musicImage;
        ImageButton favorite, playBtn;

        FavoriteMusicHolder(@NonNull View itemView) {
            super(itemView);

            musicTitle = itemView.findViewById(R.id.item_fav_music_title);
            musicAuthor = itemView.findViewById(R.id.item_fav_music_author);
            musicImage = itemView.findViewById(R.id.item_fav_music_image);
            musicCategory = itemView.findViewById(R.id.item_fav_music_category);
            musicDuration = itemView.findViewById(R.id.item_fav_music_duration);

            favorite = itemView.findViewById(R.id.item_fav_music_favorite);
            favorite.setOnClickListener(this);

            playBtn = itemView.findViewById(R.id.item_fav_play_music);
            playBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_fav_music_favorite:
                    break;
            }
        }
    }
}
