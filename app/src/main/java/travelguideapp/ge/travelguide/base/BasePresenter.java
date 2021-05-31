package travelguideapp.ge.travelguide.base;

import travelguideapp.ge.travelguide.utility.ResponseHandler;

public abstract class BasePresenter<V extends BaseListener> {

    protected abstract void attachView(V v);

    protected abstract void detachView();

    protected boolean isViewAttached(V v) {
        return v != null;
    }

    protected void onRequestFailed(ResponseHandler.Fail responseFail, V v) {
        try {
            if (isViewAttached(v)) {

                if (responseFail == null) {
                    v.onUnknownError();
                    return;
                }

                if (responseFail.getCode() == 0) {
                    v.onUnknownError();
                    return;
                }

                switch (responseFail.getCode()) {
                    case ResponseHandler.Fail.CODE_NO_CONNECTION:
                        v.onConnectionError();
                        break;
                    case ResponseHandler.Fail.CODE_CUSTOM_FAIL:
                        v.onUnknownError();
                        break;
                }

                v.hideLoader();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResponseError(ResponseHandler.Error responseError, V v) {
        try {
            if (isViewAttached(v)) {

                if (responseError == null) {
                    v.onUnknownError();
                    return;
                }

                if (responseError.getResponseCode() == 0) {
                    v.onUnknownError();
                    return;
                }

                switch (responseError.getResponseCode()) {
                    case 401:
                    case 403:
                        v.onAuthenticationError();
                        break;

                    default:
                        v.onUnknownError();
                        break;
                }

                v.hideLoader();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
