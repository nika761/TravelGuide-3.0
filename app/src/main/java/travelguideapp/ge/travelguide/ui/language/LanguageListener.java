package travelguideapp.ge.travelguide.ui.language;

import travelguideapp.ge.travelguide.model.response.AppSettingsResponse;
import travelguideapp.ge.travelguide.model.response.LanguageStringsResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;

public interface LanguageListener {

    interface SplashListener {
        void onGetLanguages(LanguagesResponse languagesResponse);

        void onGetSettings(AppSettingsResponse appSettingsResponse);


    }


    void onChooseLanguage(int languageId);

    void onGetStrings(LanguageStringsResponse languageStringsResponse);

    void onGetError(String error);

}
