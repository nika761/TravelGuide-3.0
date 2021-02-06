package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public class PostDataLoad implements Serializable, Parcelable {

    /**
     * Created by n.butskhrikidze on 01/07/2020.
     *
     * Use this key for intents
     */
    public static final String INTENT_KEY_LOAD = "post_data_load";

    /**
     * FAVORITES - get posts by user favorites.
     * MY_POSTS - get current user own posts.
     * CUSTOMER_POSTS  - get posts by selected user.
     * FEED - get posts random.
     */
    public enum Source {
        FAVORITES, MY_POSTS, CUSTOMER_POSTS, FEED, SEARCH
    }


    private int userId;
    private int scrollPosition;
    private Source loadSource;
    private List<PostResponse.Posts> posts;

    public PostDataLoad() {
    }

    public PostDataLoad(int userId, int scrollPosition, Source loadSource, List<PostResponse.Posts> posts) {
        this.userId = userId;
        this.scrollPosition = scrollPosition;
        this.loadSource = loadSource;
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

    public Source getLoadSource() {
        return loadSource;
    }

    public void setLoadSource(Source loadSource) {
        this.loadSource = loadSource;
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
        loadSource = (Source) in.readValue(Source.class.getClassLoader());
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
        dest.writeValue(loadSource);
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
