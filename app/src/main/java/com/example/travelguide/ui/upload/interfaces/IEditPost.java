package com.example.travelguide.ui.upload.interfaces;


import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.model.response.UploadPostResponseModel;

import java.util.List;


public interface IEditPost {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onSortChoose(List<ItemMedia> stories);

    void onTrimChoose(String path);

    void onStoryDeleted(List<ItemMedia> stories);

    void onStoryUploaded(UploadPostResponseModel uploadPostResponseModel);

    void onFileUploaded();

    void onFileUploadError();
}
