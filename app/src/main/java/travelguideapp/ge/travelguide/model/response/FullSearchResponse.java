package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullSearchResponse {

    @Expose
    @SerializedName("posts")
    private List<PostResponse.Posts> posts;
    @Expose
    @SerializedName("hashtags")
    private List<HashtagResponse.Hashtags> hashtags;
    @Expose
    @SerializedName("users")
    private List<Users> users;
    @Expose
    @SerializedName("status")
    private int status;

    public List<PostResponse.Posts> getPosts() {
        return posts;
    }

    public List<HashtagResponse.Hashtags> getHashtags() {
        return hashtags;
    }

    public List<Users> getUsers() {
        return users;
    }

    public int getStatus() {
        return status;
    }

    public static class Users {
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("id")
        private int id;

        public String getProfile_pic() {
            return profile_pic;
        }

        public String getLastname() {
            return lastname;
        }

        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }
    }
}
