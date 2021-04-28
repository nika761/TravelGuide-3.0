package travelguideapp.ge.travelguide.ui.music.favoriteMusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.FavoriteMusicResponse;
import travelguideapp.ge.travelguide.ui.music.PlayMusicListener;

import java.util.List;

public class FavoriteMusicAdapter extends RecyclerView.Adapter<FavoriteMusicAdapter.FavoriteMusicHolder> {
    private List<FavoriteMusicResponse.Favotite_musics> favoriteMusics;
    private int playingPosition = -1;

    private final PlayMusicListener playMusicListener;
    private final FavoriteMusicListener favoriteMusicListener;

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

    class FavoriteMusicHolder extends RecyclerView.ViewHolder {
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
            favorite.setOnClickListener(v -> {
                favoriteMusicListener.onChooseRemoveFavorite(favoriteMusics.get(getLayoutPosition()).getMusic_id());
                favoriteMusics.remove(getLayoutPosition());
                notifyItemRemoved(getLayoutPosition());
            });

            playBtn = itemView.findViewById(R.id.item_fav_play_music);
            playBtn.setOnClickListener(v -> {
                if (playingPosition == getLayoutPosition()) {
                    container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.white, null));
                    playBtn.setBackground(ContextCompat.getDrawable(musicImage.getContext(), R.drawable.icon_play));
                    playMusicListener.onChooseMusicForPlay(favoriteMusics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                    playMusicListener.onChooseMusicForPost(0);
                    playingPosition = -1;
                } else {
                    container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.greyLight, null));
                    playBtn.setBackground(ContextCompat.getDrawable(musicImage.getContext(), R.drawable.icon_pause));
                    playMusicListener.onChooseMusicForPlay(favoriteMusics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                    playMusicListener.onChooseMusicForPost(favoriteMusics.get(getLayoutPosition()).getMusic_id());
                    playingPosition = getLayoutPosition();
                }
                notifyDataSetChanged();
            });
        }

        void bindView(int position) {

            musicTitle.setText(favoriteMusics.get(position).getTitle());
            musicTitle.setSelected(true);

            musicAuthor.setText(favoriteMusics.get(position).getAuthor());
            musicAuthor.setSelected(true);

            musicDuration.setText(favoriteMusics.get(position).getDuration());

            if (playingPosition == position) {
                container.setBackgroundColor(musicImage.getContext().getResources().getColor(R.color.greyLight, null));
                playBtn.setBackground(ContextCompat.getDrawable(musicImage.getContext(), R.drawable.icon_pause));
            } else {
                container.setBackgroundColor(musicImage.getContext().getResources().getColor(R.color.white, null));
                playBtn.setBackground(ContextCompat.getDrawable(musicImage.getContext(), R.drawable.icon_play));
            }

            if (favoriteMusics.get(position).getCategories().size() != 0) {
                musicCategory.setText(favoriteMusics.get(position).getCategories().get(0).getCategory());
            }

            HelperMedia.loadPhoto(musicImage.getContext(), favoriteMusics.get(position).getImage(), musicImage);

            favorite.setBackground(ContextCompat.getDrawable(musicImage.getContext(), R.drawable.emoji_link_yellow));

        }


    }
}
