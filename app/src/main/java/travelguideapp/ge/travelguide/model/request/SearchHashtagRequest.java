package travelguideapp.ge.travelguide.model.request;

public class SearchHashtagRequest {

    private String text;

    private int from_page;

    public SearchHashtagRequest(String text, int from_page) {
        this.text = text;
        this.from_page = from_page;
    }

    public SearchHashtagRequest(String text) {
        this.text = text;
    }

}
