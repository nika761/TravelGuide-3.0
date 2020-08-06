package com.example.travelguide.ui.opening.interfaces;

import com.example.travelguide.model.response.LanguagesResponse;

public interface ILanguageActivity{
    void onGetLanguages(LanguagesResponse languagesResponse);
    void onChooseLanguage();
}
