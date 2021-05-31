package travelguideapp.ge.travelguide.model.request;

import travelguideapp.ge.travelguide.model.customModel.PostViewItem;

import java.util.List;

public class SetPostViewRequest {

    private List<PostViewItem> postViews;

    public SetPostViewRequest(List<PostViewItem> postViews) {
        this.postViews = postViews;
    }

}
