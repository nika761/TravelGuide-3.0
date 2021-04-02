package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MediaFileParams implements Parcelable, Serializable {

    /**
     * Created by n.butskhrikidze on 16/28/2020.
     * Use this key for intents
     */
    public static final String MEDIA_FILE_PARAMS = "media_files_params";

    /**
     * Video -  Fuck , this is just video.
     * Photo - Fuck , this is just photo.
     */
    public enum MediaType {
        VIDEO, PHOTO
    }

    private String mediaPath;
    private MediaType mediaType;

    public MediaFileParams(String mediaPath, MediaType mediaType) {
        this.mediaPath = mediaPath;
        this.mediaType = mediaType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    protected MediaFileParams(Parcel in) {
        mediaPath = in.readString();
        mediaType = (MediaType) in.readValue(MediaType.class.getClassLoader());
    }

    public static final Creator<MediaFileParams> CREATOR = new Creator<MediaFileParams>() {

        @Override
        public MediaFileParams createFromParcel(Parcel in) {
            return new MediaFileParams(in);
        }

        @Override
        public MediaFileParams[] newArray(int size) {
            return new MediaFileParams[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mediaPath);
        dest.writeValue(mediaType);
    }

}
