package com.travel.guide.ui.language;

import com.travel.guide.model.response.LanguageStringsResponse;
import com.travel.guide.model.response.LanguagesResponse;

public interface LanguageListener {

    interface SplashListener {
        void onGetLanguages(LanguagesResponse languagesResponse);
    }


    void onChooseLanguage(int languageId);

    void onGetStrings(LanguageStringsResponse languageStringsResponse);

    void onGetError(String error);

}
