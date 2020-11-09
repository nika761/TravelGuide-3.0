package com.travel.guide.ui.editPost;


import com.travel.guide.model.ItemMedia;
import com.travel.guide.model.response.UploadPostResponse;

import java.util.List;


public interface EditPostCallback {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onSortChoose(List<ItemMedia> stories);

    void onTrimChoose(String path, int position);

    void onStoryDeleted(List<ItemMedia> stories);

}
