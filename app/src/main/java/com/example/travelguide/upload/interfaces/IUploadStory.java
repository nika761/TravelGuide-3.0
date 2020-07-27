package com.example.travelguide.upload.interfaces;



import com.example.travelguide.model.response.UploadStoryResponseModel;


public interface IUploadStory {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onStoryUploaded(UploadStoryResponseModel uploadStoryResponseModel);

    void onFileUploaded();

    void onFileUploadError();
}
