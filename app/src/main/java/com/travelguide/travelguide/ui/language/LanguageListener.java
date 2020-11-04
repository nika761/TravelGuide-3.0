package com.travelguide.travelguide.ui.language;

import com.travelguide.travelguide.model.response.LanguagesResponse;

public interface LanguageListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onChooseLanguage();

}
