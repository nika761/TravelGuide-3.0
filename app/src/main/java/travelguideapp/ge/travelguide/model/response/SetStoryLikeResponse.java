package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetStoryLikeResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("story")
    private Story story;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Story {
        @Expose
        @SerializedName("story_likes")
        private int story_likes;
        @Expose
        @SerializedName("story_id")
        private String story_id;

        public int getStory_likes() {
            return story_likes;
        }

        public void setStory_likes(int story_likes) {
            this.story_likes = story_likes;
        }

        public String getStory_id() {
            return story_id;
        }

        public void setStory_id(String story_id) {
            this.story_id = story_id;
        }
    }
}
