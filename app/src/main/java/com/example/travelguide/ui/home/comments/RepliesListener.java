package com.example.travelguide.ui.home.comments;

import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentReplyResponse;

import java.util.List;

public interface RepliesListener {

    void onGetReplies(List<CommentResponse.Comment_reply> replies);

    void onChooseReply(int commentId);

    void onChooseLike(int commentId, int commentReplyId);

    void onLikeSuccess(LikeCommentReplyResponse likeCommentReplyResponse);

    void onLazyLoad(boolean visible, int replyCommentId);

    void onAddReply(List<CommentResponse.Comment_reply> replies);

    void onError(String message);

}
