package com.example.travelguide.ui.upload.adapter.recycler;

import android.util.SparseBooleanArray;
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
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;

import java.util.List;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.SearchMusicHolder> {
    private List<MusicResponse.Album> musics;
    private ISearchMusic iSearchMusic;
    private int playingPosition = -1;

    public SearchMusicAdapter(List<MusicResponse.Album> musics, ISearchMusic iSearchMusic) {
        this.musics = musics;
        this.iSearchMusic = iSearchMusic;
    }

    @NonNull
    @Override
    public SearchMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMusicHolder holder, int position) {
        holder.musicTitle.setText(musics.get(position).getTitle());
        holder.musicTitle.setSelected(true);
        holder.musicAuthor.setText(musics.get(position).getAuthor());
        holder.musicAuthor.setSelected(true);
        holder.musicDuration.setText(musics.get(position).getDuration());
        if (playingPosition == position) {
            holder.playBtn.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
        } else {
            holder.playBtn.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
        }
        if (musics.get(position).getCategories().size() != 0) {
            holder.musicCategory.setText(musics.get(position).getCategories().get(0).getCategory());
        }
        HelperMedia.loadPhoto(holder.musicImage.getContext(), musics.get(position).getImage(), holder.musicImage);
        if (musics.get(position).getIs_favorite() == 1) {
            holder.favorite.setBackground(holder.musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
        }
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class SearchMusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView musicTitle, musicAuthor, musicCategory, musicDuration;
        ImageView musicImage;
        ImageButton favorite, playBtn;

        SearchMusicHolder(@NonNull View itemView) {
            super(itemView);
            musicTitle = itemView.findViewById(R.id.item_music_title);
            musicAuthor = itemView.findViewById(R.id.item_music_author);
            musicImage = itemView.findViewById(R.id.item_music_image);
            musicCategory = itemView.findViewById(R.id.item_music_category);
            musicDuration = itemView.findViewById(R.id.item_music_duration);

            favorite = itemView.findViewById(R.id.item_music_favorite);
            favorite.setOnClickListener(this);

            playBtn = itemView.findViewById(R.id.item_play_music);
            playBtn.setOnClickListener(this);
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
                    iSearchMusic.onFavoriteChoose(musics.get(getLayoutPosition()).getMusic_id());
                    break;

                case R.id.item_play_music:
                    playingPosition = getLayoutPosition();
                    notifyDataSetChanged();
                    playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
                    iSearchMusic.onPressMusic(musics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                    break;
            }
        }
    }
}
