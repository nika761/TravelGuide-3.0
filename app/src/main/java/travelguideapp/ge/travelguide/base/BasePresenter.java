package travelguideapp.ge.travelguide.base;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class BasePresenter {

    private final ApiService apiService;
    private final BaseActivityListener baseActivityListener;

    public BasePresenter(BaseActivityListener baseActivityListener) {
        this.baseActivityListener = baseActivityListener;
        this.apiService = RetrofitManager.getApiService();
    }


    void setPostReport(String accessToken, SetPostReportRequest reportRequest) {
        apiService.setPostReport(accessToken, reportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetReportResponse> call, @NotNull Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            baseActivityListener.onReported(response.body());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetReportResponse> call, @NotNull Throwable t) {
            }
        });
    }

    void setUserReport(String accessToken, SetUserReportRequest setUserReportRequest) {
        apiService.setUserReport(accessToken, setUserReportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetReportResponse> call, @NotNull Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            baseActivityListener.onReported(response.body());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetReportResponse> call, @NotNull Throwable t) {
            }
        });
    }

    void setCommentReport(String accessToken, SetCommentReportRequest setCommentReportRequest) {
        apiService.setCommentReport(accessToken, setCommentReportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetReportResponse> call, @NotNull Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            baseActivityListener.onReported(response.body());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetReportResponse> call, @NotNull Throwable t) {
            }
        });
    }

}
