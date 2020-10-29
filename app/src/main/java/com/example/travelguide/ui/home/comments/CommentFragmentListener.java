package com.example.travelguide.ui.home.comments;

import com.example.travelguide.model.response.AddCommentReplyResponse;
import com.example.travelguide.model.response.AddCommentResponse;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentResponse;

public interface CommentFragmentListener {

    void onGetComments(CommentResponse commentResponse);

    void onAddComment(AddCommentResponse addCommentResponse);

    void onAddCommentReply(AddCommentReplyResponse addCommentReplyResponse);

    void onLikeChoose(int commentId);

    void onReplyChoose(int commendId);

    void onMoreCommentCallback(boolean visible, int commentId);

    void onCommentLiked(LikeCommentResponse likeCommentResponse);

    void onError(String message);

}
