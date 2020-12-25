package travelguideapp.ge.travelguide.ui.webView.about;

import travelguideapp.ge.travelguide.model.response.AboutResponse;

public interface AboutListener {
    void onGetAbout(AboutResponse.About about);

    void onGetError(String message);
}
