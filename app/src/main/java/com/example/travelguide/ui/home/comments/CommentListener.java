package com.example.travelguide.ui.home.comments;

import com.example.travelguide.model.response.AddCommentResponse;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentResponse;

import java.util.List;

public interface CommentListener {

    void onGetComments(CommentResponse commentResponse);

    void onAddComment(AddCommentResponse addCommentResponse);

    void onLikeChoose(int commentId);

    void onLikeSuccess(LikeCommentResponse likeCommentResponse);

    void onReplyChoose(CommentResponse.Post_story_comments currentComment,
                       List<CommentResponse.Comment_reply> replies, boolean requestReply);

    void onLazyLoad(boolean visible, int commentId);


    void onError(String message);

}
