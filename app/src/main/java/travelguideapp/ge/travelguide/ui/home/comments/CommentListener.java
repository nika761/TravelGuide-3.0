package travelguideapp.ge.travelguide.ui.home.comments;

import travelguideapp.ge.travelguide.model.response.AddCommentResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteCommentResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentResponse;

public interface CommentListener {

    void onGetComments(CommentResponse commentResponse);

    void onAddComment(AddCommentResponse addCommentResponse);

    void onLikeChoose(int commentId);

    void onLikeSuccess(LikeCommentResponse likeCommentResponse);

    void onDeleteChoose(int commentId);

    void onDeleted(DeleteCommentResponse deleteCommentResponse);

    void onReplyChoose(CommentResponse.Post_story_comments currentComment, boolean requestReply, int position);

    void onUserChoose(int userId);

    void onLazyLoad(boolean visible, int commentId);

    void onError(String message);

}
