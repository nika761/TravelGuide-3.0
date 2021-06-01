package travelguideapp.ge.travelguide.base;

import travelguideapp.ge.travelguide.network.ErrorHandler;
import travelguideapp.ge.travelguide.network.RequestListener;

import static travelguideapp.ge.travelguide.utility.LogUtils.LOG_E;
import static travelguideapp.ge.travelguide.utility.LogUtils.makeLogTag;

public abstract class BasePresenter<V extends BaseViewListener> implements RequestListener {

    private static final String TAG = makeLogTag(BasePresenter.class);

    protected V listener;

    protected void attachView(V listener) {
        this.listener = listener;
    }

    protected void detachView() {
        this.listener = null;
    }

    protected boolean isViewAttached() {
        return listener != null;
    }

    protected void showLoader() {
        try {
            if (isViewAttached())
                listener.showLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        try {
            if (isViewAttached()) {
                listener.hideLoader();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(ErrorHandler.FailBody responseFail) {
        try {
            if (isViewAttached()) {

                listener.hideLoader();

                if (responseFail == null || responseFail.getCode() == 0) {
                    listener.onUnknownError();
                    return;
                }

                switch (responseFail.getCode()) {
                    case ErrorHandler.FailBody.CODE_NO_CONNECTION:
                        listener.onConnectionError();
                        break;
                    case ErrorHandler.FailBody.CODE_CUSTOM_FAIL:
                        listener.onUnknownError();
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(ErrorHandler.ErrorBody responseError) {
        try {
            if (isViewAttached()) {

                listener.hideLoader();

                if (responseError == null || responseError.getResponseCode() == 0) {
                    listener.onUnknownError();
                    return;
                }

                switch (responseError.getResponseCode()) {
                    case 401:
                    case 403:
                        listener.onAuthenticationError();
                        break;

                    default:
                        listener.onUnknownError();
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
