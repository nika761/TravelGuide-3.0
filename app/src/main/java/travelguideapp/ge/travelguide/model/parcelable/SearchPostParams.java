package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchPostParams implements Parcelable {

    /**
     * Created by n.butskhrikidze on 01/07/2020.
     * Use this key for intents
     */
    public static final String INTENT_KEY_SEARCH = "post_data_search";

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

    public SearchPostParams() {
    }

    public SearchPostParams(int postId, SearchBy searchBy) {
        this.postId = postId;
        this.searchBy = searchBy;
    }

    public SearchPostParams(String hashtag, SearchBy searchBy) {
        this.hashtag = hashtag;
        this.searchBy = searchBy;
    }

    public SearchPostParams(int postId, String hashtag, SearchBy searchBy) {
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

    protected SearchPostParams(Parcel in) {
        postId = in.readInt();
        hashtag = in.readString();
        searchBy = (SearchBy) in.readValue(SearchBy.class.getClassLoader());
    }

    public static final Creator<SearchPostParams> CREATOR = new Creator<SearchPostParams>() {
        @Override
        public SearchPostParams createFromParcel(Parcel in) {
            return new SearchPostParams(in);
        }

        @Override
        public SearchPostParams[] newArray(int size) {
            return new SearchPostParams[size];
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
