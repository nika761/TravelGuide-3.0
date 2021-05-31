package travelguideapp.ge.travelguide.base;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.utility.ResponseHandler;

public abstract class BaseResponse<T> implements Callback<T> {

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            onSuccess(response);
        } else {
            onError(parseErrorBody(response));
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable throwable) {
        onFail(parseFailBody(throwable));
    }

    protected abstract void onSuccess(Response<T> response);

    protected abstract void onError(ResponseHandler.Error responseError);

    protected abstract void onFail(ResponseHandler.Fail responseFail);

    private ResponseHandler.Error parseErrorBody(Response<?> response) {

        Converter<ResponseBody, ResponseHandler.Error> bodyConverter = RetrofitManager.getRetrofit().responseBodyConverter(ResponseHandler.Error.class, new Annotation[0]);

        ResponseHandler.Error responseError;

        try {
            responseError = bodyConverter.convert(response.errorBody());
            responseError.setResponseCode(response.code());
        } catch (IOException e) {
            return null;
        }

        return responseError;

    }

    private ResponseHandler.Fail parseFailBody(Throwable throwable) {
        try {
            ResponseHandler.Fail responseFail = new ResponseHandler.Fail();

            if (throwable instanceof UnknownHostException) {
                responseFail.setCode(ResponseHandler.Fail.CODE_NO_CONNECTION);
                responseFail.setKind(ResponseHandler.Fail.KIND_NO_CONNECTION);
            } else {
                responseFail.setCode(ResponseHandler.Fail.CODE_CUSTOM_FAIL);
                responseFail.setKind(ResponseHandler.Fail.KIND_CUSTOM_FAIL);
            }
            return responseFail;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
