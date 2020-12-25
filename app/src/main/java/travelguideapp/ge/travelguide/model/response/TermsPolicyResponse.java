package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsPolicyResponse {

    @Expose
    @SerializedName("terms")
    private Terms terms;
    @Expose
    @SerializedName("policy")
    private Policy policy;
    @Expose
    @SerializedName("status")
    private int status;

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Terms {
        @Expose
        @SerializedName("terms_text")
        private String terms_text;
        @Expose
        @SerializedName("terms_title")
        private String terms_title;

        public String getTerms_text() {
            return terms_text;
        }

        public void setTerms_text(String terms_text) {
            this.terms_text = terms_text;
        }

        public String getTerms_title() {
            return terms_title;
        }

        public void setTerms_title(String terms_title) {
            this.terms_title = terms_title;
        }
    }

    public static class Policy {
        @Expose
        @SerializedName("policy_text")
        private String policy_text;
        @Expose
        @SerializedName("policy_title")
        private String policy_title;

        public String getPolicy_text() {
            return policy_text;
        }

        public void setPolicy_text(String policy_text) {
            this.policy_text = policy_text;
        }

        public String getPolicy_title() {
            return policy_title;
        }

        public void setPolicy_title(String policy_title) {
            this.policy_title = policy_title;
        }
    }
}
