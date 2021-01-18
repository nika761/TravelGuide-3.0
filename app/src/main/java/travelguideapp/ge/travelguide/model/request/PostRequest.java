package travelguideapp.ge.travelguide.model.request;

public class PostRequest {

    private int from_post_id;

    public PostRequest(int from_post_id) {
        this.from_post_id = from_post_id;
    }

    public int getFrom() {
        return from_post_id;
    }

    public void setFrom(int from_post_id) {
        this.from_post_id = from_post_id;
    }

}
