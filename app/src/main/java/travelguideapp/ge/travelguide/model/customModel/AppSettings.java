package travelguideapp.ge.travelguide.model.customModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppSettings {

    @Expose
    @SerializedName("android_version")
    private int APP_VERSION;

    @Expose
    @SerializedName("ios_version")
    private int ios_version;

    @Expose
    @SerializedName("story_view_deley_duration")
    private int POST_VIEW_TIME;

    @Expose
    @SerializedName("s3_link")
    private String S3_END_POINT;

    @Expose
    @SerializedName("s3_bucket")
    private String S3_BUCKET_NAME;

    @Expose
    @SerializedName("s3_1")
    private String S3_1;

    @Expose
    @SerializedName("s3_2")
    private String S3_2;

    @Expose
    @SerializedName("hashtag_lenght")
    private int hashtag_lenght;

    @Expose
    @SerializedName("story_video_template_for_photo")
    private String story_video_template_for_photo;

    @Expose
    @SerializedName("story_photo_crop_height")
    private int CROP_OPTION_Y;

    @Expose
    @SerializedName("story_photo_crop_width")
    private int CROP_OPTION_X;

    @Expose
    @SerializedName("age_restriction")
    private int AGE_RESTRICTION;

    @Expose
    @SerializedName("output_video_from_photo_bitrate")
    private int output_video_from_photo_bitrate;

    @Expose
    @SerializedName("output_video_bitrate")
    private int output_video_bitrate;

    @Expose
    @SerializedName("profile_posts_quntity_per_page")
    private int POST_PER_PAGE_SIZE;

    @Expose
    @SerializedName("home_posts_quntity_per_page")
    private int home_posts_quntity_per_page;

    @Expose
    @SerializedName("openapp_redirect_link")
    private String openapp_redirect_link;

    @Expose
    @SerializedName("ios_download_link")
    private String ios_download_link;

    @Expose
    @SerializedName("android_download_link")
    private String android_download_link;

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


    public int getAPP_VERSION() {
        return APP_VERSION;
    }

    public int getIos_version() {
        return ios_version;
    }

    public int getPOST_VIEW_TIME() {
        return POST_VIEW_TIME;
    }

    public String getS3_END_POINT() {
        return S3_END_POINT;
    }

    public String getS3_BUCKET_NAME() {
        return S3_BUCKET_NAME;
    }

    public String getS3_1() {
        return S3_1;
    }

    public String getS3_2() {
        return S3_2;
    }

    public String getStory_video_template_for_photo() {
        return story_video_template_for_photo;
    }

    public int getCROP_OPTION_Y() {
        return CROP_OPTION_Y;
    }

    public int getCROP_OPTION_X() {
        return CROP_OPTION_X;
    }

    public int getAGE_RESTRICTION() {
        return AGE_RESTRICTION;
    }

    public int getOutput_video_from_photo_bitrate() {
        return output_video_from_photo_bitrate;
    }

    public int getOutput_video_bitrate() {
        return output_video_bitrate;
    }

    public int getPOST_PER_PAGE_SIZE() {
        return POST_PER_PAGE_SIZE;
    }

    public int getHome_posts_quntity_per_page() {
        return home_posts_quntity_per_page;
    }

    public String getOpenapp_redirect_link() {
        return openapp_redirect_link;
    }

    public String getIos_download_link() {
        return ios_download_link;
    }

    public String getAndroid_download_link() {
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

    public int getHashtag_lenght() {
        return hashtag_lenght;
    }
}