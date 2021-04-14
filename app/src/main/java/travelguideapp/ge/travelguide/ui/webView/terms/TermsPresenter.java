package travelguideapp.ge.travelguide.ui.webView.terms;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TermsPresenter implements TermsContract.Presenter{
    private final TermsContract.View view;
    private final ApiService apiService;

    TermsPresenter(TermsContract.View view) {
        this.view = view;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    public void getTerms(TermsPolicyRequest termsPolicyRequest) {
        apiService.getTerms(termsPolicyRequest).enqueue(new Callback<TermsPolicyResponse>() {
            @Override
            public void onResponse(@NotNull Call<TermsPolicyResponse> call, @NotNull Response<TermsPolicyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        view.onGetTerms(response.body().getTerms());
                } else {
                    view.onGetError(response.message());
                }

            }

            @Override
            public void onFailure(@NotNull Call<TermsPolicyResponse> call, @NotNull Throwable t) {
                view.onGetError(t.getMessage());
            }
        });
    }
}
