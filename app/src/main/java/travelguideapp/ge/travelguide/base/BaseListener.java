package travelguideapp.ge.travelguide.base;

public interface BaseListener {

    void showLoader();

    void hideLoader();

    void attachLoader();

    void detachLoader();

    void attachPresenter();

    void detachPresenter();

    void onConnectionError();

    void onAuthenticationError();

    void onUnknownError();

}
