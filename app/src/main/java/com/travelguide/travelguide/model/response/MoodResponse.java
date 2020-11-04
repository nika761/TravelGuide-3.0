package com.travelguide.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoodResponse {

    @Expose
    @SerializedName("moods")
    private List<Moods> moods;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Moods> getMoods() {
        return moods;
    }

    public void setMoods(List<Moods> moods) {
        this.moods = moods;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Moods {
        @Expose
        @SerializedName("mood")
        private String mood;
        @Expose
        @SerializedName("id")
        private int id;

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
