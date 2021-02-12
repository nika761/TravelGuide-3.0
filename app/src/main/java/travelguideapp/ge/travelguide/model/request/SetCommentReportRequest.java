package travelguideapp.ge.travelguide.model.request;

import java.util.List;

public class SetCommentReportRequest {
    private List<Integer> report_ids;
    private int comment_id;

    public SetCommentReportRequest(int comment_id, List<Integer> report_ids) {
        this.comment_id = comment_id;
        this.report_ids = report_ids;
    }
}
