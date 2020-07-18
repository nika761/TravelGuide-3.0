package com.example.travelguide.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Post {
    public ArrayList<String> media_url;

    public Post(ArrayList<String> media_url) {
        this.media_url = media_url;
    }

    public Post() {
    }

    public ArrayList<String> getMedia_url() {
        return media_url;
    }

    public void setMedia_url(ArrayList<String> media_url) {
        this.media_url = media_url;
    }
}
