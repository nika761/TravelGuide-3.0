package travelguideapp.ge.travelguide.model.customModel;

public class ReportData {

    /**
     * USER - when reporting user.
     * POST - when reporting post.
     * COMMENT - when reporting comment.
     */
    public enum Type {
        USER, POST, COMMENT
    }

    public static ReportData getInstance(Type reportType, int id) {
        ReportData reportData = new ReportData();
        switch (reportType) {
            case COMMENT:
                reportData.setCommentId(id);
                break;
            case POST:
                reportData.setPostId(id);
                break;
            case USER:
                reportData.setUserId(id);
                break;
        }
        return reportData;
    }

    private ReportData() {
    }

    private int userId;
    private int postId;
    private int commentId;
    private Type reportType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Type getReportType() {
        return reportType;
    }

    public void setReportType(Type reportType) {
        this.reportType = reportType;
    }

}
