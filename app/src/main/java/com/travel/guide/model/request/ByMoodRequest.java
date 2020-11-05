package com.travel.guide.model.request;

public class ByMoodRequest {
    private int mood_id;

    public ByMoodRequest(int mood_id) {
        this.mood_id = mood_id;
    }

    public int getMood_id() {
        return mood_id;
    }

    public void setMood_id(int mood_id) {
        this.mood_id = mood_id;
    }
}
