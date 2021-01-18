package travelguideapp.ge.travelguide.model.customModel;

public class Report {
    String reportReason;
    int reportReasonId;

    public Report(String reportReason, int reportReasonId) {
        this.reportReason = reportReason;
        this.reportReasonId = reportReasonId;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public int getReportReasonId() {
        return reportReasonId;
    }

    public void setReportReasonId(int reportReasonId) {
        this.reportReasonId = reportReasonId;
    }
}
