package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowerResponse {
    @Expose
    @SerializedName("followers")
    private List<Followers> followers;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Followers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Followers> followers) {
        this.followers = followers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Followers {
        @Expose
        @SerializedName("is_following")
        private int is_following;

        @Expose
        @SerializedName("nickname")
        private String nickname;

        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("user_id")
        private int user_id;

        public int getIs_following() {
            return is_following;
        }

        public void setIs_following(int is_following) {
            this.is_following = is_following;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
