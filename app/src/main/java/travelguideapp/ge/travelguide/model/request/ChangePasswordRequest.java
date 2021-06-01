package travelguideapp.ge.travelguide.model.request;


public class ChangePasswordRequest {

    private final String old_password;
    private final String new_password;
    private final String confirm_password;

    public ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
        this.old_password = currentPassword;
        this.new_password = newPassword;
        this.confirm_password = confirmPassword;
    }

    public String getCurrentPassword() {
        return old_password;
    }

    public String getNewPassword() {
        return new_password;
    }

    public String getConfirmPassword() {
        return confirm_password;
    }

}
