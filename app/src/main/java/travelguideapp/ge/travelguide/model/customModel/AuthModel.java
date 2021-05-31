package travelguideapp.ge.travelguide.model.customModel;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthModel implements Parcelable {

    private int userId;
    private int userRole;
    private String loginType;
    private String accessToken;

    public AuthModel(String accessToken, int userId, int userRole, String loginType) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.userRole = userRole;
        this.loginType = loginType;
    }

    public AuthModel() {
    }

    protected AuthModel(Parcel in) {
        accessToken = in.readString();
        userId = in.readInt();
        userRole = in.readInt();
        loginType = in.readString();
    }

    public static final Creator<AuthModel> CREATOR = new Creator<AuthModel>() {
        @Override
        public AuthModel createFromParcel(Parcel in) {
            return new AuthModel(in);
        }

        @Override
        public AuthModel[] newArray(int size) {
            return new AuthModel[size];
        }
    };

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeInt(userId);
        dest.writeInt(userRole);
        dest.writeString(loginType);
    }
}
