package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetReportResponse {

    @Expose
    @SerializedName("status")
    private int status;

    public int getStatus() {
        return status;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
