package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MusicResponse {

    @Expose
    @SerializedName("album")
    private List<Album> album;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Album {
        @Expose
        @SerializedName("categories")
        private List<Categories> categories;
        @Expose
        @SerializedName("music")
        private String music;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("link")
        private String link;
        @Expose
        @SerializedName("duration")
        private String duration;
        @Expose
        @SerializedName("text_for_paste")
        private String text_for_paste;
        @Expose
        @SerializedName("text_for_page")
        private String text_for_page;
        @Expose
        @SerializedName("is_favorite")
        private int is_favorite;

        @Expose
        @SerializedName("new_label")
        private int new_label;

        @Expose
        @SerializedName("author")
        private String author;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("music_id")
        private int music_id;

        public List<Categories> getCategories() {
            return categories;
        }

        public void setCategories(List<Categories> categories) {
            this.categories = categories;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getText_for_paste() {
            return text_for_paste;
        }

        public void setText_for_paste(String text_for_paste) {
            this.text_for_paste = text_for_paste;
        }

        public String getText_for_page() {
            return text_for_page;
        }

        public void setText_for_page(String text_for_page) {
            this.text_for_page = text_for_page;
        }

        public int getIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMusic_id() {
            return music_id;
        }

        public int getNew_label() {
            return new_label;
        }

        public void setNew_label(int new_label) {
            this.new_label = new_label;
        }

        public void setMusic_id(int music_id) {
            this.music_id = music_id;
        }
    }

    public static class Categories {
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("category_id")
        private String category_id;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }
    }
}
