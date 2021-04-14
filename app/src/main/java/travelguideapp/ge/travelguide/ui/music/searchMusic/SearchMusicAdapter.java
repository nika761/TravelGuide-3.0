package travelguideapp.ge.travelguide.ui.music.searchMusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.MusicResponse;
import travelguideapp.ge.travelguide.ui.music.PlayMusicListener;

import java.util.List;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.SearchMusicHolder> {
    private List<MusicResponse.Album> musics;

    private SearchMusicListener searchMusicListener;
    //    private SearchMusicFragment.OnChooseMusic listener;
    private PlayMusicListener playMusicListener;

    private int playingPosition = -1;

    SearchMusicAdapter(SearchMusicListener searchMusicListener, PlayMusicListener playMusicListener) {
        this.searchMusicListener = searchMusicListener;
        this.playMusicListener = playMusicListener;
//        listener = (SearchMusicFragment.OnChooseMusic) context;
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

    void setMusics(List<MusicResponse.Album> musics) {
        this.musics = musics;
        notifyDataSetChanged();
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
                    try {
                        if (musics.get(getLayoutPosition()).getIs_favorite() == 0) {
                            favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
                            musics.get(getLayoutPosition()).setIs_favorite(1);
                        } else {
                            favorite.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.emoji_link_black, null));
                            musics.get(getLayoutPosition()).setIs_favorite(0);
                        }
                        searchMusicListener.onFavoriteChoose(musics.get(getLayoutPosition()).getMusic_id());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.item_play_music:
                    try {
                        if (playingPosition == getLayoutPosition()) {
                            container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.white, null));
                            playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_play, null));
                            playMusicListener.onChooseMusicForPlay(musics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                            playMusicListener.onChooseMusicForPost(0);
                            playingPosition = -1;
                        } else {
                            container.setBackgroundColor(playBtn.getContext().getResources().getColor(R.color.greyLight, null));
                            playBtn.setBackground(musicImage.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
                            playMusicListener.onChooseMusicForPlay(musics.get(getLayoutPosition()).getMusic(), getLayoutPosition());
                            playMusicListener.onChooseMusicForPost(musics.get(getLayoutPosition()).getMusic_id());
                            playingPosition = getLayoutPosition();
                        }
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
