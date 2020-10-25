package com.example.travelguide.ui.webView.about;

import com.example.travelguide.model.response.AboutResponse;

public interface AboutListener {
    void onGetAbout(AboutResponse.About about);

    void onGetError(String message);
}
