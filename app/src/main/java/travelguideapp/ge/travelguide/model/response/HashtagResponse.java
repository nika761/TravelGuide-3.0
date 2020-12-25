package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HashtagResponse {

    @Expose
    @SerializedName("hashtags")
    private List<Hashtags> hashtags;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Hashtags> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtags> hashtags) {
        this.hashtags = hashtags;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Hashtags {
        @Expose
        @SerializedName("hashtag")
        private String hashtag;
        @Expose
        @SerializedName("id")
        private int id;

        public String getHashtag() {
            return hashtag;
        }

        public void setHashtag(String hashtag) {
            this.hashtag = hashtag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
