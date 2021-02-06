package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import travelguideapp.ge.travelguide.model.customModel.AppSettings;

public class AppSettingsResponse {

    @Expose
    @SerializedName("app_settings")
    private AppSettings app_settings;

    @Expose
    @SerializedName("status")
    private int status;

    public AppSettings getApp_settings() {
        return app_settings;
    }

    public int getStatus() {
        return status;
    }

}
