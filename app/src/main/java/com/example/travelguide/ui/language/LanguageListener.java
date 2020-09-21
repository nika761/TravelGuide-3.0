package com.example.travelguide.ui.language;

import com.example.travelguide.model.response.LanguagesResponse;

public interface LanguageListener {
    void onGetLanguages(LanguagesResponse languagesResponse);
    void onChooseLanguage();
}
