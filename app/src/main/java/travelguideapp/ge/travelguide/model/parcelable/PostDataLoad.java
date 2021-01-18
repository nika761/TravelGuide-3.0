package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.model.response.PostResponse;

public class PostDataLoad implements Serializable, Parcelable {

    /**
     * Use this key for intents
     */
    public static final String INTENT_KEY_LOAD = "post_data_load";

    private int userId;
    private int scrollPosition;
    private GetPostsFrom getPostsFrom;
    private List<PostResponse.Posts> posts;

    public PostDataLoad() {
    }

    public PostDataLoad(int userId, int scrollPosition, GetPostsFrom getPostsFrom, List<PostResponse.Posts> posts) {
        this.userId = userId;
        this.scrollPosition = scrollPosition;
        this.getPostsFrom = getPostsFrom;
        this.posts = posts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public GetPostsFrom getGetPostsFrom() {
        return getPostsFrom;
    }

    public void setGetPostsFrom(GetPostsFrom getPostsFrom) {
        this.getPostsFrom = getPostsFrom;
    }

    public List<PostResponse.Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    protected PostDataLoad(Parcel in) {
        userId = in.readInt();
        scrollPosition = in.readInt();
        getPostsFrom = (GetPostsFrom) in.readValue(GetPostsFrom.class.getClassLoader());
        if (in.readByte() == 0x01) {
            posts = new ArrayList<>();
            in.readList(posts, PostResponse.Posts.class.getClassLoader());
        } else {
            posts = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(scrollPosition);
        dest.writeValue(getPostsFrom);
        if (posts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(posts);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostDataLoad> CREATOR = new Creator<PostDataLoad>() {
        @Override
        public PostDataLoad createFromParcel(Parcel in) {
            return new PostDataLoad(in);
        }

        @Override
        public PostDataLoad[] newArray(int size) {
            return new PostDataLoad[size];
        }
    };
}
