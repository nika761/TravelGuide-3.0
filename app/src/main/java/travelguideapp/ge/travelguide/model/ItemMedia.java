package travelguideapp.ge.travelguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ItemMedia implements Serializable , Parcelable {

    private int type;
    //if type == 1 video
    //if type == 0 photo

    private String path;

    public ItemMedia(int type, String path) {
        this.type = type;
        this.path = path;
    }

    protected ItemMedia(Parcel in) {
        type = in.readInt();
        path = in.readString();
    }

    public static final Creator<ItemMedia> CREATOR = new Creator<ItemMedia>() {
        @Override
        public ItemMedia createFromParcel(Parcel in) {
            return new ItemMedia(in);
        }

        @Override
        public ItemMedia[] newArray(int size) {
            return new ItemMedia[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(path);
    }
}
