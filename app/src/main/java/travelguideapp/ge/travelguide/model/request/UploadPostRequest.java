package travelguideapp.ge.travelguide.model.request;
import java.util.List;

public class UploadPostRequest {

    private List<Post_stories> post_stories;
    private List<Integer> tages_user_id;
    private List<String> hashtags;
    private int music_id;
    private String latLng;
    private String address;
    private String address_name;
    private String description;
    private String title;

    public UploadPostRequest(List<Post_stories> post_stories, List<Integer> tages_user_id, List<String> hashtags, int music_id, String latLng, String address, String address_name, String description, String title) {
        this.post_stories = post_stories;
        this.tages_user_id = tages_user_id;
        this.hashtags = hashtags;
        this.music_id = music_id;
        this.latLng = latLng;
        this.address = address;
        this.address_name = address_name;
        this.description = description;
        this.title = title;
    }

    public static class Post_stories {
        private String story_link;
        private String story_lenght;
        private int voice;
        private int type;

        public Post_stories(String story_link, String story_lenght, int voice, int type) {
            this.story_link = story_link;
            this.story_lenght = story_lenght;
            this.voice = voice;
            this.type = type;
        }
    }
}
