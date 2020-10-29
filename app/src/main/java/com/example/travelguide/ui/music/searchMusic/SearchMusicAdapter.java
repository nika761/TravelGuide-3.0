package com.example.travelguide.ui.music.searchMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.ui.gallery.GalleryFragment;

import java.util.List;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.SearchMusicHolder> {
    private List<MusicResponse.Album> musics;
    private SearchMusicListener searchMusicListener;
    private SearchMusicFragment.OnChooseMusic listener;
    private int playingPosition = -1;
    private int checkedPosition = -1;

    SearchMusicAdapter(List<MusicResponse.Album> musics, SearchMusicListener searchMusicListener, Context context) {
        this.musics = musics;
        this.searchMusicListener = searchMusicListener;
        listener = (SearchMusicFragment.OnChooseMusic) context;
    }

    @NonNull
    @Override
    public SearchMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMusicHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class SearchMusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView musicTitle, musicAuthor, musicCategory, musicDuration, musicNewLabel;
        ImageView musicImage;
        ImageButton favorite, playBtn;
        ConstraintLayout container;

        SearchMusicHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.search_music_container);
            musicTitle = itemView.findViewById(R.id.item_music_title);
            musicAuthor = itemView.findViewById(R.id.item_music_author);
            musicImage = itemView.findViewById(R.id.item_music_image);
            musicNewLabel = itemView.findViewById(R.id.item_music_added);
            musicCategory = itemView.findViewById(R.id.item_music_category);
            musicDuration = itemView.findViewById(R.id.item_music_duration);

            favorite = itemView.findViewById(R.id.item_music_favorite);
            favorite.setOnClickListener(this);

            container = itemView.findViewById(R.id.search_music_container);
            container.setOnClickListener(this);

            playBtn = itemView.findViewById(R.id.item_play_music);
            playBtn.setOnClickListener(this);
        }

        void bindView(int position) {
            musicTitle.setText(musics.get(position).getTitle());
            musicTitle.setSelected(true);

            musicAuthor.setText(musics.get(position).getAuthor());
            musicAuthor.setSelected(true);

            musicDuration.setText(musics.get(position).getDuration());

            if (musics.get(position).getNew_label() == 1) {
                musicNewLabel.setVisibility(View.VISIBLE);
            } else {
                musicNewLabel.setVisibility(View.GONE);
            }

            if (playingPosition == position) {
                container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.greyLight, null));
                playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
            } else {
                container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.white, null));
                playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
            }

            if (musics.get(position).getCategories().size() != 0) {
                musicCategory.setText(musics.get(position).getCategories().get(0).getCategory());
            }
            HelperMedia.loadPhoto(musicImage.getContext(), musics.get(position).getImage(), musicImage);

            if (musics.get(position).getIs_favorite() == 1) {
                favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
            } else {
                favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_black, null));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_music_favorite:
                    if (musics.get(getLayoutPosition()).getIs_favorite() == 0) {
                        favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
                        musics.get(getLayoutPosition()).setIs_favorite(1);
                    } else {
                        favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_black, null));
                        musics.get(getLayoutPosition()).setIs_favorite(0);
                    }
                    searchMusicListener.onFavoriteChoose(musics.get(getLayoutPosition()).getMusic_id());
                    break;

                case R.id.item_play_music:
                    if (playingPosition == getLayoutPosition()) {
                        container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.white, null));
                        playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));

                        searchMusicListener.onPressMusic(musics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                        listener.onMusicChoose(0);

                        playingPosition = -1;
                        notifyDataSetChanged();

                    } else {
                        container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.greyLight, null));
                        playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));

                        searchMusicListener.onPressMusic(musics.get(getLayoutPosition()).getMusic(), getLayoutPosition());

                        listener.onMusicChoose(musics.get(getLayoutPosition()).getMusic_id());
                        playingPosition = getLayoutPosition();
                        notifyDataSetChanged();
                    }

                    break;
            }
        }
    }
}
