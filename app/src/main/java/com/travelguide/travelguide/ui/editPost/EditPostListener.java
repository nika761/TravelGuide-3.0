package com.travelguide.travelguide.ui.editPost;


import com.travelguide.travelguide.model.ItemMedia;
import com.travelguide.travelguide.model.response.UploadPostResponse;

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
