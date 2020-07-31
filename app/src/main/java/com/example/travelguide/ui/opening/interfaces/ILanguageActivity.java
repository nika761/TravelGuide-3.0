package com.example.travelguide.ui.opening.interfaces;

import com.example.travelguide.model.response.LanguagesResponseModel;

public interface ILanguageActivity{
    void onGetLanguages(LanguagesResponseModel languagesResponseModel);
    void onChooseLanguage();
}
