package travelguideapp.ge.travelguide.network.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import travelguideapp.ge.travelguide.model.request.AuthWitFirebaseRequest;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.request.ForgotPasswordRequest;
import travelguideapp.ge.travelguide.model.request.LoginRequest;
import travelguideapp.ge.travelguide.model.request.ResetPasswordRequest;
import travelguideapp.ge.travelguide.model.request.SignUpRequest;
import travelguideapp.ge.travelguide.model.request.SignUpWithFirebaseRequest;
import travelguideapp.ge.travelguide.model.request.VerifyEmailRequest;
import travelguideapp.ge.travelguide.model.response.AuthWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;
import travelguideapp.ge.travelguide.model.response.LoginResponse;
import travelguideapp.ge.travelguide.model.response.ResetPasswordResponse;
import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.model.response.SignUpWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.VerifyEmailResponse;

public interface AuthorizationApi {

    @POST("register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("login")
    Call<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("register_outer_account")
    Call<SignUpWithFirebaseResponse> signUpWithFirebase(@Body SignUpWithFirebaseRequest signUpWithFirebaseRequest);

    @POST("login/by_outer_account")
    Call<AuthWithFirebaseResponse> signInWithFirebase(@Body AuthWitFirebaseRequest authWitFirebaseRequest);

    @POST("password/email")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("password/reset")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @POST("change/password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("email/verify_email")
    Call<VerifyEmailResponse> verifyEmail(@Body VerifyEmailRequest verifyEmailRequest);

    @POST("check_nick")
    Call<CheckNickResponse> checkNick(@Body CheckNickRequest checkNickRequest);

}
