package com.travelguide.travelguide.ui.home.comments;

import com.travelguide.travelguide.model.request.AddCommentReplyRequest;
import com.travelguide.travelguide.model.request.DeleteReplyRequest;
import com.travelguide.travelguide.model.request.GetMoreCommentRequest;
import com.travelguide.travelguide.model.request.LikeCommentReplyRequest;
import com.travelguide.travelguide.model.response.AddCommentReplyResponse;
import com.travelguide.travelguide.model.response.DeleteReplyResponse;
import com.travelguide.travelguide.model.response.LikeCommentReplyResponse;
import com.travelguide.travelguide.model.response.MoreReplyResponse;
import com.travelguide.travelguide.network.ApiService;
import com.travelguide.travelguide.network.RetrofitManager;

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

    void getReplies(String accessToken, GetMoreCommentRequest getMoreCommentRequest) {
        apiService.getMoreCommentReplies(accessToken, getMoreCommentRequest).enqueue(new Callback<MoreReplyResponse>() {
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

    void addCommentReply(String accessToken, AddCommentReplyRequest addCommentReplyRequest) {
        apiService.addStoryCommentReply(accessToken, addCommentReplyRequest).enqueue(new Callback<AddCommentReplyResponse>() {
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

    void addCommentReplyLike(String accessToken, LikeCommentReplyRequest likeCommentReplyRequest) {
        apiService.likeCommentReply(accessToken, likeCommentReplyRequest).enqueue(new Callback<LikeCommentReplyResponse>() {
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

    void deleteCommentReply(String accessToken, DeleteReplyRequest deleteReplyRequest) {
        apiService.deleteStoryCommentReply(accessToken, deleteReplyRequest).enqueue(new Callback<DeleteReplyResponse>() {
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
