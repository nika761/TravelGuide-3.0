package travelguideapp.ge.travelguide.model.customModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public class AppSettings implements Parcelable {

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


    protected AppSettings(Parcel in) {
        APP_VERSION = in.readInt();
        ios_version = in.readInt();
        POST_VIEW_TIME = in.readInt();
        S3_END_POINT = in.readString();
        S3_BUCKET_NAME = in.readString();
        S3_1 = in.readString();
        S3_2 = in.readString();
        hashtag_lenght = in.readInt();
        story_video_template_for_photo = in.readString();
        CROP_OPTION_Y = in.readInt();
        CROP_OPTION_X = in.readInt();
        AGE_RESTRICTION = in.readInt();
        output_video_from_photo_bitrate = in.readInt();
        output_video_bitrate = in.readInt();
        POST_PER_PAGE_SIZE = in.readInt();
        home_posts_quntity_per_page = in.readInt();
        openapp_redirect_link = in.readString();
        ios_download_link = in.readString();
        android_download_link = in.readString();
        registration_deley = in.readInt();
        post_location_radius = in.readInt();
        post_view_deley = in.readInt();
        story_video_duration_max = in.readInt();
        post_description_lenght = in.readInt();
        story_photo_quantity = in.readInt();
        story_photo_duration = in.readInt();
        story_video_duration_min = in.readInt();
        post_videos_duration = in.readInt();
    }

    public static final Creator<AppSettings> CREATOR = new Creator<AppSettings>() {
        @Override
        public AppSettings createFromParcel(Parcel in) {
            return new AppSettings(in);
        }

        @Override
        public AppSettings[] newArray(int size) {
            return new AppSettings[size];
        }
    };

    public int getAppVersion() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(APP_VERSION);
        dest.writeInt(ios_version);
        dest.writeInt(POST_VIEW_TIME);
        dest.writeString(S3_END_POINT);
        dest.writeString(S3_BUCKET_NAME);
        dest.writeString(S3_1);
        dest.writeString(S3_2);
        dest.writeInt(hashtag_lenght);
        dest.writeString(story_video_template_for_photo);
        dest.writeInt(CROP_OPTION_Y);
        dest.writeInt(CROP_OPTION_X);
        dest.writeInt(AGE_RESTRICTION);
        dest.writeInt(output_video_from_photo_bitrate);
        dest.writeInt(output_video_bitrate);
        dest.writeInt(POST_PER_PAGE_SIZE);
        dest.writeInt(home_posts_quntity_per_page);
        dest.writeString(openapp_redirect_link);
        dest.writeString(ios_download_link);
        dest.writeString(android_download_link);
        dest.writeInt(registration_deley);
        dest.writeInt(post_location_radius);
        dest.writeInt(post_view_deley);
        dest.writeInt(story_video_duration_max);
        dest.writeInt(post_description_lenght);
        dest.writeInt(story_photo_quantity);
        dest.writeInt(story_photo_duration);
        dest.writeInt(story_video_duration_min);
        dest.writeInt(post_videos_duration);
    }

    public static AppSettings create(String serializedData) {
        Gson gson = new Gson();
        return gson.fromJson(serializedData, AppSettings.class);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
