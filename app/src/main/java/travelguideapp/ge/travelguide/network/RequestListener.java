package travelguideapp.ge.travelguide.network;

public interface RequestListener {

    void onFail(ErrorHandler.FailBody responseFail);

    void onError(ErrorHandler.ErrorBody responseError);

    void onSuccess();

}
