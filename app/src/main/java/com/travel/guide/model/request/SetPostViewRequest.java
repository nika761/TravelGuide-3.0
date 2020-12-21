package com.travel.guide.model.request;

import com.travel.guide.model.PostView;

import java.util.List;

public class SetPostViewRequest {

    private List<PostView> postViews;

    public SetPostViewRequest(List<PostView> postViews) {
        this.postViews = postViews;
    }

}
