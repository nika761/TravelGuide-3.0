package com.example.travelguide.model.request;

public class ChangeLangRequestModel {
    String language_id;

    public ChangeLangRequestModel(String language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

}
