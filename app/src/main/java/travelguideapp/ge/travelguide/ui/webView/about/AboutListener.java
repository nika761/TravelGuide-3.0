package travelguideapp.ge.travelguide.ui.webView.about;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.AboutResponse;

public interface AboutListener extends BaseViewListener {
    void onGetAbout(AboutResponse.About about);
}
