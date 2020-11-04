package com.travelguide.travelguide.ui.home.profile.changeLanguage;

import com.travelguide.travelguide.model.response.ChangeLangResponse;
import com.travelguide.travelguide.model.response.LanguagesResponse;

public interface ChangeLangListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onLanguageChange(ChangeLangResponse changeLangResponse);

    void onLanguageChoose(int langId);

}
