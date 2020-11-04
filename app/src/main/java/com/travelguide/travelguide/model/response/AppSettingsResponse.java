package com.travelguide.travelguide.model.response;

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

    public int getStatus() {
        return status;
    }

    public static class App_settings {
        @Expose
        @SerializedName("story_video_template_for_photo")
        private int story_video_template_for_photo;
        @Expose
        @SerializedName("story_photo_crop_height")
        private int story_photo_crop_height;
        @Expose
        @SerializedName("story_photo_crop_width")
        private int story_photo_crop_width;
        @Expose
        @SerializedName("age_restriction")
        private int age_restriction;
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
        @SerializedName("story_video_duration_max")
        private int story_video_duration_max;
        @Expose
        @SerializedName("post_description_lenght")
        private int post_description_lenght;
        @Expose
        @SerializedName("story_photo_quantity")
        private int story_photo_quantity;
        @Expose
        @SerializedName("story_photo_duration")
        private int story_photo_duration;
        @Expose
        @SerializedName("story_video_duration_min")
        private int story_video_duration_min;
        @Expose
        @SerializedName("post_videos_duration")
        private int post_videos_duration;

        public int getStory_video_template_for_photo() {
            return story_video_template_for_photo;
        }

        public int getStory_photo_crop_height() {
            return story_photo_crop_height;
        }

        public int getStory_photo_crop_width() {
            return story_photo_crop_width;
        }

        public int getAge_restriction() {
            return age_restriction;
        }

        public int getOutput_video_from_photo_bitrate() {
            return output_video_from_photo_bitrate;
        }

        public int getOutput_video_bitrate() {
            return output_video_bitrate;
        }

        public int getProfile_posts_quntity_per_page() {
            return profile_posts_quntity_per_page;
        }

        public int getHome_posts_quntity_per_page() {
            return home_posts_quntity_per_page;
        }

        public int getOpenapp_redirect_link() {
            return openapp_redirect_link;
        }

        public int getIos_download_link() {
            return ios_download_link;
        }

        public int getAndroid_download_link() {
            return android_download_link;
        }

        public int getRegistration_deley() {
            return registration_deley;
        }

        public int getPost_location_radius() {
            return post_location_radius;
        }

        public int getPost_view_deley() {
            return post_view_deley;
        }

        public int getStory_video_duration_max() {
            return story_video_duration_max;
        }

        public int getPost_description_lenght() {
            return post_description_lenght;
        }

        public int getStory_photo_quantity() {
            return story_photo_quantity;
        }

        public int getStory_photo_duration() {
            return story_photo_duration;
        }

        public int getStory_video_duration_min() {
            return story_video_duration_min;
        }

        public int getPost_videos_duration() {
            return post_videos_duration;
        }
    }
}
