package travelguideapp.ge.travelguide.base;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.parcelable.ReportParams;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.network.api.ReportApi;

public class HomeParentPresenter {

    private final ReportApi reportApi;
    private final HomeParentListener homeParentListener;

    public HomeParentPresenter(HomeParentListener homeParentListener) {
        this.homeParentListener = homeParentListener;
        this.reportApi = RetrofitManager.getReportApi();
    }

    public void setReport(Object request, ReportParams.Type reportType) {
        switch (reportType) {
            case COMMENT:
                reportApi.setCommentReport(((SetCommentReportRequest) request)).enqueue(reportCallback());
                break;
            case POST:
                reportApi.setPostReport(((SetPostReportRequest) request)).enqueue(reportCallback());
                break;
            case USER:
                reportApi.setUserReport(((SetUserReportRequest) request)).enqueue(reportCallback());
                break;

        }
    }

    private Callback<SetReportResponse> reportCallback() {
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
