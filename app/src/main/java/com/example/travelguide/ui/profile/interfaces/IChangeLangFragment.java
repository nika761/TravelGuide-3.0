package com.example.travelguide.ui.profile.interfaces;

import com.example.travelguide.model.response.ChangeLangResponse;
import com.example.travelguide.model.response.LanguagesResponse;

public interface IChangeLangFragment {

    void onGetLanguages(LanguagesResponse languagesResponse);

    void onLanguageChange(ChangeLangResponse changeLangResponse);

    void onLanguageChoose(int langId);

}
