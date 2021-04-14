package travelguideapp.ge.travelguide.ui.profile.changeLanguage;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.ChangeLangRequest;
import travelguideapp.ge.travelguide.model.response.ChangeLangResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ChangeLangPresenter {
    private ChangeLangListener iChangeLangFragment;
    private ApiService apiService;

    ChangeLangPresenter(ChangeLangListener iChangeLangFragment) {
        this.iChangeLangFragment = iChangeLangFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sentLanguageRequest() {
        apiService.getLanguages().enqueue(new Callback<LanguagesResponse>() {
            @Override
            public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
                if (response.isSuccessful()) {
                    iChangeLangFragment.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponse> call, Throwable t) {
                iChangeLangFragment.onError();
            }
        });
    }

    void sentChangeLanguageRequest(ChangeLangRequest changeLangRequest) {
        apiService.changeLanguage(changeLangRequest).enqueue(new Callback<ChangeLangResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChangeLangResponse> call, @NotNull Response<ChangeLangResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null)
                        if (response.body().getStatus() == 0)
                            iChangeLangFragment.onLanguageChange(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ChangeLangResponse> call, @NotNull Throwable t) {
                iChangeLangFragment.onError();
            }
        });
    }
}
