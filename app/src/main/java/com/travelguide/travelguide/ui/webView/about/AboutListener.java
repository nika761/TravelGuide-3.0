package com.travelguide.travelguide.ui.webView.about;

import com.travelguide.travelguide.model.response.AboutResponse;

public interface AboutListener {
    void onGetAbout(AboutResponse.About about);

    void onGetError(String message);
}
