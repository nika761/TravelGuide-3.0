package travelguideapp.ge.travelguide.network;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {

    private final RequestListener requestListener;

    public BaseCallback(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            onSuccessParent();
            onSuccess(response);
        } else {
            onError(response);
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable throwable) {
        onFail(throwable);
    }

    private void onFail(Throwable throwable) {
        try {
            if (requestListener == null) {
                return;
            }
            requestListener.onFail(ErrorHandler.FailBody.from(throwable));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onError(Response<T> response) {
        try {
            if (requestListener == null) {
                return;
            }
            requestListener.onError(ErrorHandler.ErrorBody.from(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSuccessParent() {
        try {
            if (requestListener == null) {
                return;
            }
            requestListener.onSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onSuccess(Response<T> response);

}
