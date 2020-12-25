package travelguideapp.ge.travelguide.model.request;

public class ProfileRequest {
    private int user_id;

    public ProfileRequest(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
