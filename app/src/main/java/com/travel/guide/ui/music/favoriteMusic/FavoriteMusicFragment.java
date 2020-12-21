package com.travel.guide.ui.music.favoriteMusic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.MyToaster;
import com.travel.guide.model.request.AddFavoriteMusic;
import com.travel.guide.model.response.AddFavoriteMusicResponse;
import com.travel.guide.ui.music.PlayMusicListener;
import com.travel.guide.ui.music.searchMusic.SearchMusicFragment;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.model.response.FavoriteMusicResponse;

import java.util.List;


public class FavoriteMusicFragment extends Fragment implements FavoriteMusicListener {

    public static FavoriteMusicFragment getInstance(PlayMusicListener playMusicListener) {
        FavoriteMusicFragment favoriteMusicFragment = new FavoriteMusicFragment();
        favoriteMusicFragment.playMusicListener = playMusicListener;
        return favoriteMusicFragment;
    }

    private PlayMusicListener playMusicListener;

    private RecyclerView favoriteMusicRecycler;
    private FavoriteMusicAdapter favoriteMusicAdapter;
    private FavoriteMusicPresenter favoriteMusicPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_music, container, false);
        favoriteMusicRecycler = view.findViewById(R.id.favorite_music_recycler);
        favoriteMusicRecycler.setLayoutManager(new LinearLayoutManager(favoriteMusicRecycler.getContext()));
        favoriteMusicRecycler.setHasFixedSize(true);

        favoriteMusicPresenter = new FavoriteMusicPresenter(this);

        return view;
    }

    @Override
    public void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics) {
        try {
            if (favoriteMusicAdapter == null) {
                favoriteMusicAdapter = new FavoriteMusicAdapter(playMusicListener, this);
                favoriteMusicAdapter.setFavoriteMusics(favoriteMusics);
                favoriteMusicRecycler.setAdapter(favoriteMusicAdapter);
            } else {
                favoriteMusicAdapter.setFavoriteMusics(favoriteMusics);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFavoriteRemoved(AddFavoriteMusicResponse addFavoriteMusicResponse) {
        if (addFavoriteMusicResponse != null)
            try {
                Log.e("favoriteRemoved", String.valueOf(addFavoriteMusicResponse.getStatus()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onChooseRemoveFavorite(int musicId) {
        favoriteMusicPresenter.removeFavorite(GlobalPreferences.getAccessToken(favoriteMusicRecycler.getContext()), new AddFavoriteMusic(musicId));
    }

    @Override
    public void onError(String message) {
        MyToaster.getErrorToaster(favoriteMusicRecycler.getContext(), message);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (favoriteMusicPresenter != null)
            favoriteMusicPresenter.getFavoriteMusics(GlobalPreferences.getAccessToken(favoriteMusicRecycler.getContext()));
    }

    @Override
    public void onDestroy() {
        if (favoriteMusicPresenter != null) {
            favoriteMusicPresenter = null;
        }
        super.onDestroy();
    }
}
