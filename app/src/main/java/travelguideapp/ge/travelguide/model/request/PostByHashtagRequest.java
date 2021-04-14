package travelguideapp.ge.travelguide.model.request;

public class PostByHashtagRequest {

    private final String hashtag;
    private final int from_post_id;

    public PostByHashtagRequest(String hashtag, int from_post_id) {
        this.hashtag = hashtag;
        this.from_post_id = from_post_id;
    }

}
