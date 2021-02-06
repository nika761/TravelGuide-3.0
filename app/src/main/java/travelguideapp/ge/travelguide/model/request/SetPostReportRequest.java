package travelguideapp.ge.travelguide.model.request;

import java.util.List;

public class SetPostReportRequest {

    private final List<Integer> report_ids;
    private int post_id;

    public SetPostReportRequest(int post_id, List<Integer> report_ids) {
        this.post_id = post_id;
        this.report_ids = report_ids;
    }

}
