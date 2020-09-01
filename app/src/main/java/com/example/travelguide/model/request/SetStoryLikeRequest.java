package com.example.travelguide.model.request;

public class SetStoryLikeRequest {

    private int story_id;
    private int post_id;

    public SetStoryLikeRequest(int story_id, int post_id) {
        this.story_id = story_id;
        this.post_id = post_id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }
}
