package com.travelguide.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutResponse {

    @Expose
    @SerializedName("about")
    private About about;

    @Expose
    @SerializedName("status")
    private int status;

    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class About {
        @Expose
        @SerializedName("about_text")
        private String about_text;
        @Expose
        @SerializedName("about_title")
        private String about_title;

        public String getAbout_text() {
            return about_text;
        }

        public void setAbout_text(String about_text) {
            this.about_text = about_text;
        }

        public String getAbout_title() {
            return about_title;
        }

        public void setAbout_title(String about_title) {
            this.about_title = about_title;
        }
    }
}
