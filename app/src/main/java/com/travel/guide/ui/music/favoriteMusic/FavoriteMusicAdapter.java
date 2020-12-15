package com.travel.guide.ui.music.favoriteMusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.FavoriteMusicResponse;
import com.travel.guide.ui.music.PlayMusicListener;

import java.util.List;

public class FavoriteMusicAdapter extends RecyclerView.Adapter<FavoriteMusicAdapter.FavoriteMusicHolder> {
    private List<FavoriteMusicResponse.Favotite_musics> favoriteMusics;
    private int playingPosition = -1;

    private PlayMusicListener playMusicListener;
    private FavoriteMusicListener favoriteMusicListener;

    FavoriteMusicAdapter(PlayMusicListener playMusicListener, FavoriteMusicListener favoriteMusicListener) {
        this.playMusicListener = playMusicListener;
        this.favoriteMusicListener = favoriteMusicListener;
    }

    @NonNull
    @Override
    public FavoriteMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteMusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMusicHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return favoriteMusics.size();
    }

    void setFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics) {
        this.favoriteMusics = favoriteMusics;
        notifyDataSetChanged();
    }

    class FavoriteMusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView musicTitle, musicAuthor, musicCategory, musicDuration;
        ImageView musicImage;
        ImageButton favorite, playBtn;
        ConstraintLayout container;


        FavoriteMusicHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.favorite_music_container);
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

        void bindView(int position) {

            musicTitle.setText(favoriteMusics.get(position).getTitle());
            musicTitle.setSelected(true);

            musicAuthor.setText(favoriteMusics.get(position).getAuthor());
            musicAuthor.setSelected(true);

            musicDuration.setText(favoriteMusics.get(position).getDuration());

            if (playingPosition == position) {
                container.setBackgroundColor(musicImage.getContext().getResources().getColor(R.color.greyLight, null));
                playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
            } else {
                container.setBackgroundColor(musicImage.getContext().getResources().getColor(R.color.white, null));
                playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
            }

            if (favoriteMusics.get(position).getCategories().size() != 0) {
                musicCategory.setText(favoriteMusics.get(position).getCategories().get(0).getCategory());
            }

            HelperMedia.loadPhoto(musicImage.getContext(), favoriteMusics.get(position).getImage(), musicImage);

            favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_fav_music_favorite:
                    favoriteMusicListener.onChooseRemoveFavorite(favoriteMusics.get(getLayoutPosition()).getMusic_id());
                    favoriteMusics.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    break;

                case R.id.item_fav_play_music:
                    if (playingPosition == getLayoutPosition()) {
                        container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.white, null));
                        playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
                        playMusicListener.onChooseMusicToPlay(favoriteMusics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                        playMusicListener.onChooseMusicForPost(0);
                        playingPosition = -1;
                        notifyDataSetChanged();
                    } else {
                        container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.greyLight, null));
                        playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
                        playMusicListener.onChooseMusicToPlay(favoriteMusics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                        playMusicListener.onChooseMusicForPost(favoriteMusics.get(getLayoutPosition()).getMusic_id());
                        playingPosition = getLayoutPosition();
                        notifyDataSetChanged();
                    }
                    break;

            }
        }
    }
}
