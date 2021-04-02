package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class PostSearchParams implements Parcelable {

    /**
     * Created by n.butskhrikidze on 01/07/2020.
     * Use this key for intents
     */
    public static final String POST_SEARCH_PARAMS = "post_search_params";

    /**
     * Hahtags - Search posts by hashtags.
     * Location - Search posts by location.
     */
    public enum SearchBy {
        HASHTAG, LOCATION
    }

    private int postId;
    private String hashtag;
    private SearchBy searchBy;

    public PostSearchParams() {
    }

    public PostSearchParams(int postId, SearchBy searchBy) {
        this.postId = postId;
        this.searchBy = searchBy;
    }

    public PostSearchParams(String hashtag, SearchBy searchBy) {
        this.hashtag = hashtag;
        this.searchBy = searchBy;
    }

    public PostSearchParams(int postId, String hashtag, SearchBy searchBy) {
        this.postId = postId;
        this.hashtag = hashtag;
        this.searchBy = searchBy;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public SearchBy getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(SearchBy searchBy) {
        this.searchBy = searchBy;
    }

    protected PostSearchParams(Parcel in) {
        postId = in.readInt();
        hashtag = in.readString();
        searchBy = (SearchBy) in.readValue(SearchBy.class.getClassLoader());
    }

    public static final Creator<PostSearchParams> CREATOR = new Creator<PostSearchParams>() {
        @Override
        public PostSearchParams createFromParcel(Parcel in) {
            return new PostSearchParams(in);
        }

        @Override
        public PostSearchParams[] newArray(int size) {
            return new PostSearchParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postId);
        dest.writeString(hashtag);
        dest.writeValue(searchBy);
    }
}
