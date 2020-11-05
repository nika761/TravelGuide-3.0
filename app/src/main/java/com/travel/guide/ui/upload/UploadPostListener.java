package com.travel.guide.ui.upload;

public interface UploadPostListener {

    void onPostUploadedToS3();

    void onPostUploadErrorS3(String message);

    void onPostUploaded();

    void onPostUploadError(String message);

}
