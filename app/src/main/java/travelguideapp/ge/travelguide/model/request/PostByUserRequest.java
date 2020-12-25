package travelguideapp.ge.travelguide.model.request;

public class PostByUserRequest {
    private int user_id;
    private int from_post_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public PostByUserRequest(int user_id, int from_post_id) {
        this.user_id = user_id;
        this.from_post_id = from_post_id;
    }
}
