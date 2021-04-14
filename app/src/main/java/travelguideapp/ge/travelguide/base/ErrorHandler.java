package travelguideapp.ge.travelguide.base;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ErrorHandler<T> extends Callback<T> {
    @Override
    void onResponse(@NotNull Call<T> call, @NotNull Response<T> response);

    @Override
    void onFailure(@NotNull Call<T> call, @NotNull Throwable throwable);
}
