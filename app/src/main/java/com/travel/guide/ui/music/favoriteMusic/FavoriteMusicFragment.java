package com.travel.guide.ui.music.favoriteMusic;

import android.os.Bundle;
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
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.model.response.FavoriteMusicResponse;

import java.util.List;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FavoriteMusicFragment extends Fragment implements FavoriteMusicListener {
    private RecyclerView favoriteMusicRecycler;
    private FavoriteMusicPresenter favoriteMusicPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_music, container, false);
        favoriteMusicRecycler = view.findViewById(R.id.favorite_music_recycler);
        favoriteMusicPresenter = new FavoriteMusicPresenter(this);
        return view;
    }

    @Override
    public void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics) {
        favoriteMusicRecycler.setLayoutManager(new LinearLayoutManager(favoriteMusicRecycler.getContext()));
        favoriteMusicRecycler.setAdapter(new FavoriteMusicAdapter(favoriteMusics));
        favoriteMusicRecycler.setHasFixedSize(true);
    }

    @Override
    public void onGetFavoriteFailed(String message) {
        Toast.makeText(favoriteMusicRecycler.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteMusicPresenter.getFavoriteMusics(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(favoriteMusicRecycler.getContext()));
    }

    @Override
    public void onDestroy() {
        if (favoriteMusicPresenter != null) {
            favoriteMusicPresenter = null;
        }
        super.onDestroy();
    }
}
