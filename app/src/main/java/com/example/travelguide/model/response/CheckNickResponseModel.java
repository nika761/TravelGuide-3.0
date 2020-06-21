package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckNickResponseModel {


    @Expose
    @SerializedName("nicknames_to_offer")
    private List<String> nicknames_to_offer;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private int status;

    public List<String> getNicknames_to_offer() {
        return nicknames_to_offer;
    }

    public void setNicknames_to_offer(List<String> nicknames_to_offer) {
        this.nicknames_to_offer = nicknames_to_offer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
