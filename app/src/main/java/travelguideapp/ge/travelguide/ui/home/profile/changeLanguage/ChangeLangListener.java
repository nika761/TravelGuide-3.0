package travelguideapp.ge.travelguide.ui.home.profile.changeLanguage;

import travelguideapp.ge.travelguide.model.response.ChangeLangResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;

public interface ChangeLangListener {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onLanguageChange(ChangeLangResponse changeLangResponse);

    void onLanguageChoose(int langId);

    void onError();

}
