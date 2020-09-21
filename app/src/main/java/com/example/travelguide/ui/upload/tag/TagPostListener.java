package com.example.travelguide.ui.upload.tag;

import com.example.travelguide.model.response.HashtagResponse;

import java.util.List;

interface TagPostListener {
    void onGetHashtags(List<HashtagResponse.Hashtags> hashtags);

    void onGetHashtagsError(String message);

    void onChooseHashtag(String hashtag);
}
