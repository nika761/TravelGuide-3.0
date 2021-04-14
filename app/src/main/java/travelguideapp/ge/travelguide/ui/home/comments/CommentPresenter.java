package travelguideapp.ge.travelguide.ui.home.comments;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.AddCommentRequest;
import travelguideapp.ge.travelguide.model.request.CommentRequest;
import travelguideapp.ge.travelguide.model.request.DeleteCommentRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentRequest;
import travelguideapp.ge.travelguide.model.response.AddCommentResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteCommentResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CommentPresenter {
    private final CommentListener commentListener;
    private final ApiService apiService;

    CommentPresenter(CommentListener commentListener) {
        this.commentListener = commentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getComments( CommentRequest commentRequest) {
        apiService.getStoryComments(commentRequest).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(@NotNull Call<CommentResponse> call, @NotNull Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        commentListener.onGetComments(response.body());
                    else
                        commentListener.onError(response.body().getStatus() + " ");
                } else {
                    commentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void addComment( AddCommentRequest addCommentRequest) {
        apiService.addStoryComment(addCommentRequest).enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddCommentResponse> call, @NotNull Response<AddCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        commentListener.onAddComment(response.body());
                    } else {
                        commentListener.onError(response.body().getStatus() + " ");
                    }
                } else {
                    commentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddCommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void likeComment( LikeCommentRequest likeCommentRequest) {
        apiService.likeStoryComment(likeCommentRequest).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(@NotNull Call<LikeCommentResponse> call, @NotNull Response<LikeCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentListener.onLikeSuccess(response.body());
                } else {
                    commentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LikeCommentResponse> call, @NotNull Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void deleteComment( DeleteCommentRequest deleteCommentRequest) {
        apiService.deleteStoryComment(deleteCommentRequest).enqueue(new Callback<DeleteCommentResponse>() {
            @Override
            public void onResponse(@NotNull Call<DeleteCommentResponse> call, @NotNull Response<DeleteCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        commentListener.onDeleted(response.body());
                    else
                        commentListener.onError(response.body().getStatus() + response.message());
                } else {
                    commentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeleteCommentResponse> call, @NotNull Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

}
