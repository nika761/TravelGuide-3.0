package com.example.travelguide.opening.interfaces;

import com.example.travelguide.model.response.LanguagesResponseModel;

public interface ILanguageActivity{
    void onGetLanguages(LanguagesResponseModel languagesResponseModel);
    void onChooseLanguage();
}
