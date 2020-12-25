package travelguideapp.ge.travelguide.ui.music.searchMusic;

import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.MoodResponse;
import travelguideapp.ge.travelguide.model.response.MusicResponse;

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
