package travelguideapp.ge.travelguide.ui.home.comments;

import travelguideapp.ge.travelguide.model.response.AddCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteReplyResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentReplyResponse;

import java.util.List;

public interface RepliesListener {

    void onGetReplies(List<CommentResponse.Comment_reply> replies);

    void onChooseReply(int commentId);

    void onReplyAdded(AddCommentReplyResponse addCommentReplyResponse);


    void onChooseLike(int commentId, int commentReplyId);

    void onLiked(LikeCommentReplyResponse likeCommentReplyResponse);


    void onChooseDelete(int replyId);

    void onChooseReport(int replyId);

    void onDeleted(DeleteReplyResponse deleteReplyResponse);


    void requestLazyLoad(boolean visible, int replyCommentId);

    void onError(String message);

}
