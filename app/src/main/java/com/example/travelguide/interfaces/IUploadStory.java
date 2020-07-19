package com.example.travelguide.interfaces;


import android.net.Uri;

import com.example.travelguide.model.response.UploadStoryResponseModel;

import java.io.File;

public interface IUploadStory {
    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onStoryUploaded(UploadStoryResponseModel uploadStoryResponseModel);

    void onFileUploaded();

    void onFileUploadError();
}
