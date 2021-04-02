package travelguideapp.ge.travelguide.base;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.customModel.ReportParams;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class HomeParentPresenter {

    private final ApiService apiService;
    private final HomeParentListener homeParentListener;

    public HomeParentPresenter(HomeParentListener homeParentListener) {
        this.homeParentListener = homeParentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void setReport(String accessToken, Object request, ReportParams.Type reportType) {
        switch (reportType) {
            case COMMENT:
                apiService.setCommentReport(accessToken, ((SetCommentReportRequest) request)).enqueue(getCallback());
                break;
            case POST:
                apiService.setPostReport(accessToken, ((SetPostReportRequest) request)).enqueue(getCallback());
                break;
            case USER:
                apiService.setUserReport(accessToken, ((SetUserReportRequest) request)).enqueue(getCallback());
                break;

        }
    }

    private Callback<SetReportResponse> getCallback() {
        return new Callback<SetReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetReportResponse> call, @NotNull Response<SetReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                        case 1:
                            homeParentListener.onReported(response.body());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetReportResponse> call, @NotNull Throwable t) {

            }
        };
    }

}
