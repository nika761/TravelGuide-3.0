package com.example.travelguide.ui.upload.presenter;

import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;

public class SearchMusicPresenter {
    ISearchMusic iSearchMusic;
    ApiService apiService;

    public SearchMusicPresenter(ISearchMusic iSearchMusic) {
        this.iSearchMusic = iSearchMusic;
        this.apiService = RetrofitManager.getApiService();
    }
}
