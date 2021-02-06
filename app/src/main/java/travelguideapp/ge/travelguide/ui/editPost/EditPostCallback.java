package travelguideapp.ge.travelguide.ui.editPost;


import travelguideapp.ge.travelguide.model.customModel.ItemMedia;
import travelguideapp.ge.travelguide.model.parcelable.MediaFileData;

import java.util.List;


public interface EditPostCallback {

    void onCropChoose(String path, int position);

    void onFilterChoose(String path, int position);

    void onSortChoose(List<MediaFileData> stories);

    void onTrimChoose(String path, int position);

    void onStoryDeleted(List<MediaFileData> stories);

}
