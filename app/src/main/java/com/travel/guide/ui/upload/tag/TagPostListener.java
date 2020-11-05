package com.travel.guide.ui.upload.tag;

import com.travel.guide.model.response.FollowerResponse;
import com.travel.guide.model.response.HashtagResponse;

import java.util.List;

interface TagPostListener {
    void onGetHashtags(List<HashtagResponse.Hashtags> hashtags);

    void onGetError(String message);

    void onChooseHashtag(String hashtag);

    void onGetFollowers(List<FollowerResponse.Followers> followers);

    void onChooseFollower(int followerId, String name);
}
