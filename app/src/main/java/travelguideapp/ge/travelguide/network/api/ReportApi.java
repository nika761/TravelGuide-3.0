package travelguideapp.ge.travelguide.network.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;

public interface ReportApi {

    @POST("set/post_report")
    Call<SetReportResponse> setPostReport(@Body SetPostReportRequest setReportRequest);


    @POST("set/user_report")
    Call<SetReportResponse> setUserReport(@Body SetUserReportRequest setUserReportRequest);


    @POST("set/comment_report")
    Call<SetReportResponse> setCommentReport(@Body SetCommentReportRequest setCommentReportRequest);

}

