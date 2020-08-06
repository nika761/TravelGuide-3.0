package com.example.travelguide.ui.upload.fragment;

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

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.ui.upload.adapter.recycler.FavoriteMusicAdapter;
import com.example.travelguide.ui.upload.interfaces.IFavoriteMusic;
import com.example.travelguide.ui.upload.presenter.FavoriteMusicPresenter;

import java.util.List;

public class FavoriteMusicFragment extends Fragment implements IFavoriteMusic {
    private RecyclerView favoriteMusicRecycler;
    private FavoriteMusicPresenter favoriteMusicPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_music, container, false);
        favoriteMusicRecycler = view.findViewById(R.id.favorite_music_recycler);
        favoriteMusicPresenter = new FavoriteMusicPresenter(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteMusicPresenter.getFavoriteMusics("Bearer" + " " + HelperPref.getCurrentAccessToken(favoriteMusicRecycler.getContext()));
    }

    @Override
    public void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics) {
        if (favoriteMusics.size() == 0) {
            favoriteMusicRecycler.setLayoutManager(new LinearLayoutManager(favoriteMusicRecycler.getContext()));
            FavoriteMusicAdapter favoriteMusicAdapter = new FavoriteMusicAdapter(favoriteMusics);
            favoriteMusicRecycler.setAdapter(favoriteMusicAdapter);
        }
    }

    @Override
    public void onGetFavoriteFailed() {
        Toast.makeText(favoriteMusicRecycler.getContext(), "Get Favorite error", Toast.LENGTH_LONG).show();
    }
}
