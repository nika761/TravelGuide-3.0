package com.example.travelguide.ui.editPost;


import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.model.response.UploadPostResponse;

import java.util.List;


public interface EditPostListener {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onSortChoose(List<ItemMedia> stories);

    void onTrimChoose(String path, int position);

    void onStoryDeleted(List<ItemMedia> stories);

    void onStoryUploaded(UploadPostResponse uploadPostResponse);

    void onFileUploaded();

    void onFileUploadError();
}
