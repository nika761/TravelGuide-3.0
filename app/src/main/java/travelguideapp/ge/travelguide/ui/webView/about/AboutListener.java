package travelguideapp.ge.travelguide.ui.webView.about;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.AboutResponse;

public interface AboutListener extends BaseListener {
    void onGetAbout(AboutResponse.About about);
}
