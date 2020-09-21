package com.example.travelguide.ui.home.profile.changeLanguage;

import com.example.travelguide.model.response.ChangeLangResponse;
import com.example.travelguide.model.response.LanguagesResponse;

public interface ChangeLangListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onLanguageChange(ChangeLangResponse changeLangResponse);

    void onLanguageChoose(int langId);

}
