package travelguideapp.ge.travelguide.model.request;

import java.util.List;

public class SetUserReportRequest {

    private final List<Integer> report_ids;
    private final int user_id;

    public SetUserReportRequest(int user_id, List<Integer> report_ids) {
        this.user_id = user_id;
        this.report_ids = report_ids;
    }

}
