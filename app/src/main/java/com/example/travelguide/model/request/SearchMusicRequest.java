package com.example.travelguide.model.request;

public class SearchMusicRequest {
    private String text;

    public SearchMusicRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
