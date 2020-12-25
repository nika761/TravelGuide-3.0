package travelguideapp.ge.travelguide.ui.home.comments;

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
    private CommentListener commentListener;
    private ApiService apiService;

    CommentPresenter(CommentListener commentListener) {
        this.commentListener = commentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getComments(String accessToken, CommentRequest commentRequest) {
        apiService.getStoryComments(accessToken, commentRequest).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
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
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void addComment(String accessToken, AddCommentRequest addCommentRequest) {
        apiService.addStoryComment(accessToken, addCommentRequest).enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
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
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void likeComment(String accessToken, LikeCommentRequest likeCommentRequest) {
        apiService.likeStoryComment(accessToken, likeCommentRequest).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentListener.onLikeSuccess(response.body());
                } else {
                    commentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

    void deleteComment(String accessToken, DeleteCommentRequest deleteCommentRequest) {
        apiService.deleteStoryComment(accessToken, deleteCommentRequest).enqueue(new Callback<DeleteCommentResponse>() {
            @Override
            public void onResponse(Call<DeleteCommentResponse> call, Response<DeleteCommentResponse> response) {
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
            public void onFailure(Call<DeleteCommentResponse> call, Throwable t) {
                commentListener.onError(t.getMessage());
            }
        });
    }

}
