package com.travel.guide.model.request;

import java.util.List;

public class UploadPostRequest {
    private int music_id;
    private List<String> photo;
    private List<String> video;

    public UploadPostRequest(int music_id, List<String> photo, List<String> video) {
        this.music_id = music_id;
        this.photo = photo;
        this.video = video;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

}
