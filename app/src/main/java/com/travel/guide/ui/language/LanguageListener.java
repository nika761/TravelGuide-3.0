package com.travel.guide.ui.language;

import com.travel.guide.model.response.LanguagesResponse;

public interface LanguageListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onChooseLanguage();

}
