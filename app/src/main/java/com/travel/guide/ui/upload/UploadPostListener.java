package com.travel.guide.ui.upload;

public interface UploadPostListener {

    void onPostUploadedToS3();

    void onPostUploaded();

    void onPostUploadError(String message);

}
