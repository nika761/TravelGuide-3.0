package travelguideapp.ge.travelguide.model.request;

public class TermsPolicyRequest {
    private int language_id;

    public TermsPolicyRequest(int language_id) {
        this.language_id = language_id;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

}
