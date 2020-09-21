package com.example.travelguide.ui.home.comments;

import com.example.travelguide.model.response.CommentResponse;

public interface CommentFragmentListener {

    void onGetComments(CommentResponse commentResponse);

    void onGetCommentsError(String message);

}
