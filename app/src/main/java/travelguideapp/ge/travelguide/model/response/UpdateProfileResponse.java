package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateProfileResponse {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("body")
    private String body;

    @Expose
    @SerializedName("nicknames_to_offer")
    private List<String> nicknames_to_offer;

    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public List<String> getNicknames_to_offer() {
        return nicknames_to_offer;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
