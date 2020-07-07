package com.example.travelguide.interfaces;


import android.net.Uri;

import com.example.travelguide.model.response.UploadStoryResponse;

public interface IUploadStory {
    void onGetItem(Uri uri,int position);
    void onStoryUploaded(UploadStoryResponse uploadStoryResponse);
}
