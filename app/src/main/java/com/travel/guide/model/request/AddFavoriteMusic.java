package com.travel.guide.model.request;

public class AddFavoriteMusic {
    private int music_id;

    public AddFavoriteMusic(int music_id) {
        this.music_id = music_id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

}
