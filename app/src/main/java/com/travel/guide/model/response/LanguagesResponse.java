package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LanguagesResponse {

    @Expose
    @SerializedName("language")
    private List<Language> language;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Language implements Serializable {

        @Expose
        @SerializedName("flag_link")
        private String flag_link;
        @Expose
        @SerializedName("native_full")
        private String native_full;
        @Expose
        @SerializedName("native_short")
        private String native_short;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("short_name")
        private String short_name;
        @Expose
        @SerializedName("id")
        private int id;

        public String getFlag_link() {
            return flag_link;
        }

        public void setFlag_link(String flag_link) {
            this.flag_link = flag_link;
        }

        public String getNative_full() {
            return native_full;
        }

        public void setNative_full(String native_full) {
            this.native_full = native_full;
        }

        public String getNative_short() {
            return native_short;
        }

        public void setNative_short(String native_short) {
            this.native_short = native_short;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
