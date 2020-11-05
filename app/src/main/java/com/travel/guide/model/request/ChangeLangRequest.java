package com.travel.guide.model.request;

public class ChangeLangRequest {
    private String language_id;

    public ChangeLangRequest(String language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

}
