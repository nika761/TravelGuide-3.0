package com.travelguide.travelguide.ui.music.searchMusic;

import com.travelguide.travelguide.model.response.AddFavoriteMusicResponse;
import com.travelguide.travelguide.model.response.MoodResponse;
import com.travelguide.travelguide.model.response.MusicResponse;

import java.util.List;

public interface SearchMusicListener {
    void onGetMusic(MusicResponse musicResponse);

    void onStopMusic();

    void onPressMusic(String music, int position);

    void onFavoriteChoose(int musicId);

    void onFavoriteAdded(AddFavoriteMusicResponse addFavoriteMusicResponse);

    void onGetMoods(List<MoodResponse.Moods> moods);

    void onGetError(String message);

    void onChooseMood(int moodId);
}
