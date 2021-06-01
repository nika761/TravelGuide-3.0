package travelguideapp.ge.travelguide.network;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class ErrorHandler {

    public static class FailBody {

        public static final String KIND_NO_CONNECTION = "KIND_NO_CONNECTION";
        public static final String KIND_CUSTOM_FAIL = "KIND_CUSTOM_FAIL";

        public static final int CODE_NO_CONNECTION = 900;
        public static final int CODE_CUSTOM_FAIL = 901;

        private String kind;
        private int code;

        public FailBody() {
        }

        public static FailBody from(Throwable throwable) throws Exception {
            try {
                FailBody responseFail = new FailBody();

                if (throwable instanceof UnknownHostException) {
                    responseFail.setCode(CODE_NO_CONNECTION);
                    responseFail.setKind(KIND_NO_CONNECTION);
                } else {
                    responseFail.setCode(CODE_CUSTOM_FAIL);
                    responseFail.setKind(KIND_CUSTOM_FAIL);
                }
                return responseFail;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        @Override
        public String toString() {
            return "ResponseFail{" +
                    " kind='" + kind + '\'' +
                    " code='" + code + '\'' +
                    '}';
        }
    }

    public static class ErrorBody {

        private String message;
        private int responseCode;
        private int status;

        public ErrorBody() {
        }

        public static ErrorBody from(Response<?> response) throws Exception {

            Converter<ResponseBody, ErrorBody> bodyConverter = RetrofitManager.getRetrofit().
                    responseBodyConverter(ErrorBody.class, new Annotation[0]);

            ErrorBody responseError;

            try {
                responseError = bodyConverter.convert(response.errorBody());
                responseError.setResponseCode(response.code());
            } catch (IOException e) {
                return null;
            }

            return responseError;

        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        @Override
        public String toString() {
            return "Error{" +
                    "  message=" + message + '\'' +
                    "  responseCode=" + responseCode + '\'' +
                    "  status=" + status +
                    '}';
        }

    }


}
