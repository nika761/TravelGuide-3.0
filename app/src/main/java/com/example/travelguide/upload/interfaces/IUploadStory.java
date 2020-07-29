package com.example.travelguide.upload.interfaces;


import com.example.travelguide.model.response.UploadStoryResponseModel;

import java.util.ArrayList;


public interface IUploadStory {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onSortChoose(ArrayList<String> stories);

    void onStoryDeleted(ArrayList<String> stories);

    void onStoryUploaded(UploadStoryResponseModel uploadStoryResponseModel);

    void onFileUploaded();

    void onFileUploadError();
}
