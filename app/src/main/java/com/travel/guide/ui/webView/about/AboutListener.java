package com.travel.guide.ui.webView.about;

import com.travel.guide.model.response.AboutResponse;

public interface AboutListener {
    void onGetAbout(AboutResponse.About about);

    void onGetError(String message);
}
