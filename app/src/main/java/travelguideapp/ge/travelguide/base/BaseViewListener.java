package travelguideapp.ge.travelguide.base;

public interface BaseViewListener {

    void showLoader();

    void hideLoader();

    void attachLoader();

    void detachLoader();

    void detachPresenter();

    void onConnectionError();

    void onAuthenticationError();

    void onUnknownError();

}
