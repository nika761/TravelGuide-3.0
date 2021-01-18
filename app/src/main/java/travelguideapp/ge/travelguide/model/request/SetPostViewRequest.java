package travelguideapp.ge.travelguide.model.request;

import travelguideapp.ge.travelguide.model.customModel.PostView;

import java.util.List;

public class SetPostViewRequest {

    private List<PostView> postViews;

    public SetPostViewRequest(List<PostView> postViews) {
        this.postViews = postViews;
    }

}
