package travelguideapp.ge.travelguide.model;

public class FireBaseUserModel {
    private String email;
    private String name;
    private String lastName;
    private String id;
    private String pictureUrl;
    private String token;

    public FireBaseUserModel(String email, String name, String lastName, String id, String pictureUrl, String token) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
