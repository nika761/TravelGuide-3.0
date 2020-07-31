package com.example.travelguide.ui.upload.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.ui.upload.adapter.recycler.SearchMusicAdapter;

public class SearchMusicFragment extends Fragment {
    private SearchMusicAdapter searchMusicAdapter;
    private RecyclerView searchMusicRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        searchMusicRecycler = view.findViewById(R.id.search_music_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void initRecycler() {
        searchMusicRecycler.setLayoutManager(new LinearLayoutManager(searchMusicRecycler.getContext()));
        searchMusicAdapter = new SearchMusicAdapter();
        searchMusicRecycler.setAdapter(searchMusicAdapter);
    }
}
