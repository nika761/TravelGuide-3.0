package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppSettingsResponse {

    @Expose
    @SerializedName("app_settings")
    private App_settings app_settings;
    @Expose
    @SerializedName("status")
    private int status;

    public App_settings getApp_settings() {
        return app_settings;
    }

    public void setApp_settings(App_settings app_settings) {
        this.app_settings = app_settings;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class App_settings {
        @Expose
        @SerializedName("output_video_from_photo_bitrate")
        private int output_video_from_photo_bitrate;
        @Expose
        @SerializedName("output_video_bitrate")
        private int output_video_bitrate;
        @Expose
        @SerializedName("profile_posts_quntity_per_page")
        private int profile_posts_quntity_per_page;
        @Expose
        @SerializedName("home_posts_quntity_per_page")
        private int home_posts_quntity_per_page;
        @Expose
        @SerializedName("openapp_redirect_link")
        private int openapp_redirect_link;
        @Expose
        @SerializedName("ios_download_link")
        private int ios_download_link;
        @Expose
        @SerializedName("android_download_link")
        private int android_download_link;
        @Expose
        @SerializedName("registration_deley")
        private int registration_deley;
        @Expose
        @SerializedName("post_location_radius")
        private int post_location_radius;
        @Expose
        @SerializedName("post_view_deley")
        private int post_view_deley;
        @Expose
        @SerializedName("storage_video_duration_max")
        private int storage_video_duration_max;
        @Expose
        @SerializedName("post_description_lenght")
        private int post_description_lenght;
        @Expose
        @SerializedName("storage_photo_quantity")
        private int storage_photo_quantity;
        @Expose
        @SerializedName("storage_photo_duration")
        private int storage_photo_duration;
        @Expose
        @SerializedName("storage_video_duration_min")
        private int storage_video_duration_min;
        @Expose
        @SerializedName("post_videos_duration")
        private int post_videos_duration;

        public int getOutput_video_from_photo_bitrate() {
            return output_video_from_photo_bitrate;
        }

        public void setOutput_video_from_photo_bitrate(int output_video_from_photo_bitrate) {
            this.output_video_from_photo_bitrate = output_video_from_photo_bitrate;
        }

        public int getOutput_video_bitrate() {
            return output_video_bitrate;
        }

        public void setOutput_video_bitrate(int output_video_bitrate) {
            this.output_video_bitrate = output_video_bitrate;
        }

        public int getProfile_posts_quntity_per_page() {
            return profile_posts_quntity_per_page;
        }

        public void setProfile_posts_quntity_per_page(int profile_posts_quntity_per_page) {
            this.profile_posts_quntity_per_page = profile_posts_quntity_per_page;
        }

        public int getHome_posts_quntity_per_page() {
            return home_posts_quntity_per_page;
        }

        public void setHome_posts_quntity_per_page(int home_posts_quntity_per_page) {
            this.home_posts_quntity_per_page = home_posts_quntity_per_page;
        }

        public int getOpenapp_redirect_link() {
            return openapp_redirect_link;
        }

        public void setOpenapp_redirect_link(int openapp_redirect_link) {
            this.openapp_redirect_link = openapp_redirect_link;
        }

        public int getIos_download_link() {
            return ios_download_link;
        }

        public void setIos_download_link(int ios_download_link) {
            this.ios_download_link = ios_download_link;
        }

        public int getAndroid_download_link() {
            return android_download_link;
        }

        public void setAndroid_download_link(int android_download_link) {
            this.android_download_link = android_download_link;
        }

        public int getRegistration_deley() {
            return registration_deley;
        }

        public void setRegistration_deley(int registration_deley) {
            this.registration_deley = registration_deley;
        }

        public int getPost_location_radius() {
            return post_location_radius;
        }

        public void setPost_location_radius(int post_location_radius) {
            this.post_location_radius = post_location_radius;
        }

        public int getPost_view_deley() {
            return post_view_deley;
        }

        public void setPost_view_deley(int post_view_deley) {
            this.post_view_deley = post_view_deley;
        }

        public int getStorage_video_duration_max() {
            return storage_video_duration_max;
        }

        public void setStorage_video_duration_max(int storage_video_duration_max) {
            this.storage_video_duration_max = storage_video_duration_max;
        }

        public int getPost_description_lenght() {
            return post_description_lenght;
        }

        public void setPost_description_lenght(int post_description_lenght) {
            this.post_description_lenght = post_description_lenght;
        }

        public int getStorage_photo_quantity() {
            return storage_photo_quantity;
        }

        public void setStorage_photo_quantity(int storage_photo_quantity) {
            this.storage_photo_quantity = storage_photo_quantity;
        }

        public int getStorage_photo_duration() {
            return storage_photo_duration;
        }

        public void setStorage_photo_duration(int storage_photo_duration) {
            this.storage_photo_duration = storage_photo_duration;
        }

        public int getStorage_video_duration_min() {
            return storage_video_duration_min;
        }

        public void setStorage_video_duration_min(int storage_video_duration_min) {
            this.storage_video_duration_min = storage_video_duration_min;
        }

        public int getPost_videos_duration() {
            return post_videos_duration;
        }

        public void setPost_videos_duration(int post_videos_duration) {
            this.post_videos_duration = post_videos_duration;
        }
    }
}
