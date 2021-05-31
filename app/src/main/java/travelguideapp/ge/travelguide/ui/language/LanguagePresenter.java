package travelguideapp.ge.travelguide.ui.language;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.LanguageStringsRequest;
import travelguideapp.ge.travelguide.model.response.AppSettingsResponse;
import travelguideapp.ge.travelguide.model.response.LanguageStringsResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguagePresenter {
    private LanguageListener languageListener;
    private LanguageListener.SplashListener splashListener;
    private ApiService service;

    LanguagePresenter(LanguageListener languageListener) {
        this.languageListener = languageListener;
        this.service = RetrofitManager.getApiService();
    }

    public LanguagePresenter(LanguageListener.SplashListener splashListener) {
        this.splashListener = splashListener;
        this.service = RetrofitManager.getApiService();
    }

    public void sentLanguageRequest() {
        service.getLanguages().enqueue(new Callback<LanguagesResponse>() {
            @Override
            public void onResponse(@NotNull Call<LanguagesResponse> call, @NotNull Response<LanguagesResponse> response) {
                if (response.isSuccessful()) {
                    splashListener.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LanguagesResponse> call, @NotNull Throwable t) {

            }
        });
    }


    public void getAppSettings() {
        service.getAppSettings().enqueue(new Callback<AppSettingsResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppSettingsResponse> call, @NotNull Response<AppSettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    splashListener.onGetSettings(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppSettingsResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

    }


    void getLanguageStrings(LanguageStringsRequest languageStringsRequest) {
        service.getLanguageStrings(languageStringsRequest).enqueue(new Callback<LanguageStringsResponse>() {
            @Override
            public void onResponse(@NotNull Call<LanguageStringsResponse> call, @NotNull Response<LanguageStringsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        if (response.body().getStatus() == 0)
                            languageListener.onGetStrings(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    languageListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LanguageStringsResponse> call, @NotNull Throwable t) {
                languageListener.onGetError(t.getMessage());
            }
        });
    }
}
