package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public class PostHomeParams implements Serializable, Parcelable {

    /**
     * Created by n.butskhrikidze on 01/07/2020.
     * <p>
     * Use this key for intents
     */
    public static final String POST_HOME_PARAMS = "post_home_params";

    /**
     * FAVORITES - get posts by user favorites.
     * MY_POSTS - get current user own posts.
     * CUSTOMER_POSTS  - get posts by selected user.
     * FEED - get posts random.
     * SEARCH - get posts by search.
     */
    public enum Type {
        FAVORITES, MY_POSTS, CUSTOMER_POSTS, FEED, SEARCH
    }


    private int userId;
    private int scrollPosition;
    private Type pageType;
    private List<PostResponse.Posts> posts;

    public PostHomeParams() {
    }

    public PostHomeParams(int userId, int scrollPosition, Type pageType, List<PostResponse.Posts> posts) {
        this.userId = userId;
        this.scrollPosition = scrollPosition;
        this.pageType = pageType;
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

    public Type getPageType() {
        return pageType;
    }

    public void setPageType(Type pageType) {
        this.pageType = pageType;
    }

    public List<PostResponse.Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    protected PostHomeParams(Parcel in) {
        userId = in.readInt();
        scrollPosition = in.readInt();
        pageType = (Type) in.readValue(Type.class.getClassLoader());
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
        dest.writeValue(pageType);
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

    public static final Creator<PostHomeParams> CREATOR = new Creator<PostHomeParams>() {
        @Override
        public PostHomeParams createFromParcel(Parcel in) {
            return new PostHomeParams(in);
        }

        @Override
        public PostHomeParams[] newArray(int size) {
            return new PostHomeParams[size];
        }
    };
}
