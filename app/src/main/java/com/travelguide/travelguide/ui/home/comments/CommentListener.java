package com.travelguide.travelguide.ui.home.comments;

import com.travelguide.travelguide.model.response.AddCommentResponse;
import com.travelguide.travelguide.model.response.CommentResponse;
import com.travelguide.travelguide.model.response.DeleteCommentResponse;
import com.travelguide.travelguide.model.response.LikeCommentResponse;

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
