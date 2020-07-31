package com.example.travelguide.ui.profile.interfaces;

import com.example.travelguide.model.response.ChangeLangResponseModel;
import com.example.travelguide.model.response.LanguagesResponseModel;

public interface IChangeLangFragment {

    void onGetLanguages(LanguagesResponseModel languagesResponseModel);

    void onLanguageChange(ChangeLangResponseModel changeLangResponseModel);

    void onLanguageChoose(int langId);

}
