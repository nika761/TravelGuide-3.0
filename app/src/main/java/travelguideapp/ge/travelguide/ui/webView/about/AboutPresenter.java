package travelguideapp.ge.travelguide.ui.webView.about;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.AboutRequest;
import travelguideapp.ge.travelguide.model.response.AboutResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AboutPresenter {
    private final AboutListener iAboutFragment;
    private final ApiService apiService;

    AboutPresenter(AboutListener iAboutFragment) {
        this.iAboutFragment = iAboutFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sendAboutRequest(AboutRequest aboutRequest) {
        apiService.getAbout(aboutRequest).enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(@NotNull Call<AboutResponse> call, @NotNull Response<AboutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        iAboutFragment.onGetAbout(response.body().getAbout());
                } else {
                    iAboutFragment.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AboutResponse> call, @NotNull Throwable t) {
                iAboutFragment.onGetError(t.getMessage());
            }
        });
    }

}
