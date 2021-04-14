package travelguideapp.ge.travelguide.ui.home.comments;

import travelguideapp.ge.travelguide.model.request.AddCommentReplyRequest;
import travelguideapp.ge.travelguide.model.request.DeleteReplyRequest;
import travelguideapp.ge.travelguide.model.request.GetMoreCommentRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentReplyRequest;
import travelguideapp.ge.travelguide.model.response.AddCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.DeleteReplyResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.MoreReplyResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepliesPresenter {

    private RepliesListener repliesListener;
    private ApiService apiService;

    RepliesPresenter(RepliesListener repliesListener) {
        this.repliesListener = repliesListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getReplies(GetMoreCommentRequest getMoreCommentRequest) {
        apiService.getMoreCommentReplies( getMoreCommentRequest).enqueue(new Callback<MoreReplyResponse>() {
            @Override
            public void onResponse(Call<MoreReplyResponse> call, Response<MoreReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    repliesListener.onGetReplies(response.body().getComment_replies());
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MoreReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }

    void addCommentReply(AddCommentReplyRequest addCommentReplyRequest) {
        apiService.addStoryCommentReply( addCommentReplyRequest).enqueue(new Callback<AddCommentReplyResponse>() {
            @Override
            public void onResponse(Call<AddCommentReplyResponse> call, Response<AddCommentReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        repliesListener.onReplyAdded(response.body());
                    } else {
                        repliesListener.onError(String.valueOf(response.body().getStatus()));
                    }
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddCommentReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }

    void addCommentReplyLike(LikeCommentReplyRequest likeCommentReplyRequest) {
        apiService.likeCommentReply( likeCommentReplyRequest).enqueue(new Callback<LikeCommentReplyResponse>() {
            @Override
            public void onResponse(Call<LikeCommentReplyResponse> call, Response<LikeCommentReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    repliesListener.onLiked(response.body());
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LikeCommentReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }

    void deleteCommentReply(DeleteReplyRequest deleteReplyRequest) {
        apiService.deleteStoryCommentReply( deleteReplyRequest).enqueue(new Callback<DeleteReplyResponse>() {
            @Override
            public void onResponse(Call<DeleteReplyResponse> call, Response<DeleteReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 3:
                            repliesListener.onDeleted(response.body());
                            break;
                        default:
                            repliesListener.onError(response.body().getStatus() + response.message());
                    }
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }
}
