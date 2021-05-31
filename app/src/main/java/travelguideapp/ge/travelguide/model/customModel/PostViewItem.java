package travelguideapp.ge.travelguide.model.customModel;

public class PostViewItem {

    private int post_id;
    private int story_id;

    public PostViewItem(int post_id, int story_id) {
        this.post_id = post_id;
        this.story_id = story_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

}
