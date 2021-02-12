package travelguideapp.ge.travelguide.base;

import android.util.Log;

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

    public BasePresenter() {
        this.apiService = RetrofitManager.getApiService();
    }

    private final ApiService apiService;

    void setPostReport(String accessToken, SetPostReportRequest reportRequest) {
        apiService.setPostReport(accessToken, reportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(Call<SetReportResponse> call, Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SetReportResponse> call, Throwable t) {
            }
        });
    }

    void setUserReport(String accessToken, SetUserReportRequest setUserReportRequest) {
        apiService.setUserReport(accessToken, setUserReportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(Call<SetReportResponse> call, Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SetReportResponse> call, Throwable t) {
            }
        });
    }

    void setCommentReport(String accessToken, SetCommentReportRequest setCommentReportRequest) {
        apiService.setCommentReport(accessToken, setCommentReportRequest).enqueue(new Callback<SetReportResponse>() {
            @Override
            public void onResponse(Call<SetReportResponse> call, Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SetReportResponse> call, Throwable t) {
            }
        });
    }

}
