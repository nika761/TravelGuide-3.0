package travelguideapp.ge.travelguide.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ReportParams implements Serializable, Parcelable {

    /**
     * USER - when reporting user.
     * POST - when reporting post.
     * COMMENT - when reporting comment.
     */
    public enum Type {
        USER, POST, COMMENT
    }

    public static ReportParams setParams(Type reportType, int id) {
        ReportParams reportParams = new ReportParams();
        switch (reportType) {
            case COMMENT:
                reportParams.setCommentId(id);
                reportParams.setReportType(reportType);
                break;
            case POST:
                reportParams.setPostId(id);
                reportParams.setReportType(reportType);
                break;
            case USER:
                reportParams.setUserId(id);
                reportParams.setReportType(reportType);
                break;
        }
        return reportParams;
    }

    private ReportParams() {
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

    protected ReportParams(Parcel in) {
        userId = in.readInt();
        postId = in.readInt();
        commentId = in.readInt();
        reportType = (Type) in.readSerializable();
    }

    public static final Creator<ReportParams> CREATOR = new Creator<ReportParams>() {
        @Override
        public ReportParams createFromParcel(Parcel in) {
            return new ReportParams(in);
        }

        @Override
        public ReportParams[] newArray(int size) {
            return new ReportParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(postId);
        dest.writeInt(commentId);
        dest.writeSerializable(reportType);
    }

}
