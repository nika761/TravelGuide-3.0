package com.travel.guide.ui.home.profile.changeLanguage;

import com.travel.guide.model.response.ChangeLangResponse;
import com.travel.guide.model.response.LanguagesResponse;

public interface ChangeLangListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onLanguageChange(ChangeLangResponse changeLangResponse);

    void onLanguageChoose(int langId);

    void onError();

}
