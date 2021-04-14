package travelguideapp.ge.travelguide.base;

import android.app.AlertDialog;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class BaseResponse<T> implements ErrorHandler<T> {

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
//            switch (response.body().getStatus()) {
//                case -100:
//                case -101:
//                    homePageListener.onAuthenticationError("Sign In Again");
//                    break;
//
//                case 0:
//                    homePageListener.onGetProfile(response.body().getUserinfo());
//                    break;
//            }
        } else {
            switch (response.code()) {
                case 401:
                    //handle unauthorized request and logout
                    break;

                case 403:
                    break;
            }
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {

    }

}
