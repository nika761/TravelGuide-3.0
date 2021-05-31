package travelguideapp.ge.travelguide.utility;

public class ResponseHandler {

    public static class Fail {

        public static final String KIND_NO_CONNECTION = "KIND_NO_CONNECTION";
        public static final String KIND_CUSTOM_FAIL = "KIND_CUSTOM_FAIL";

        public static final int CODE_NO_CONNECTION = 900;
        public static final int CODE_CUSTOM_FAIL = 901;

        private String kind;
        private int code;

        public Fail() {
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

    public static class Error {

        private String message;

        private int responseCode;
        private int status;

        public Error() {
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
