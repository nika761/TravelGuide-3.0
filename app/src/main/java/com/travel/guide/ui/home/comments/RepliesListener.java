package com.travel.guide.ui.home.comments;

import com.travel.guide.model.response.AddCommentReplyResponse;
import com.travel.guide.model.response.CommentResponse;
import com.travel.guide.model.response.DeleteReplyResponse;
import com.travel.guide.model.response.LikeCommentReplyResponse;

import java.util.List;

public interface RepliesListener {

    void onGetReplies(List<CommentResponse.Comment_reply> replies);

    void onChooseReply(int commentId);

    void onReplyAdded(AddCommentReplyResponse addCommentReplyResponse);


    void onChooseLike(int commentId, int commentReplyId);

    void onLiked(LikeCommentReplyResponse likeCommentReplyResponse);


    void onChooseDelete(int replyId);

    void onDeleted(DeleteReplyResponse deleteReplyResponse);


    void requestLazyLoad(boolean visible, int replyCommentId);

    void onError(String message);

}
