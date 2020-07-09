package com.example.travelguide.model;

import android.graphics.Bitmap;

import java.util.List;

public class Post {
    public List<Bitmap> posts;

    public List<Bitmap> getPosts() {
        return posts;
    }

    public void setPosts(List<Bitmap> posts) {
        this.posts = posts;
    }

    public Post(List<Bitmap> posts) {
        this.posts = posts;
    }
}
