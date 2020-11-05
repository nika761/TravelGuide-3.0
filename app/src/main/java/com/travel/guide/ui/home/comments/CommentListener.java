package com.travel.guide.ui.home.comments;

import com.travel.guide.model.response.AddCommentResponse;
import com.travel.guide.model.response.CommentResponse;
import com.travel.guide.model.response.DeleteCommentResponse;
import com.travel.guide.model.response.LikeCommentResponse;

public interface CommentListener {

    void onGetComments(CommentResponse commentResponse);

    void onAddComment(AddCommentResponse addCommentResponse);

    void onLikeChoose(int commentId);

    void onLikeSuccess(LikeCommentResponse likeCommentResponse);

    void onDeleteChoose(int commentId);

    void onDeleted(DeleteCommentResponse deleteCommentResponse);

    void onReplyChoose(CommentResponse.Post_story_comments currentComment, boolean requestReply, int position);

    void onLazyLoad(boolean visible, int commentId);

    void onError(String message);

}
