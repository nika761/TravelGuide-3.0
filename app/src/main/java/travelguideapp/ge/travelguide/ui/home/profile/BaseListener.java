package travelguideapp.ge.travelguide.ui.home.profile;

public interface BaseListener {

    void onError(String message);

    void onAuthenticationError(String message);

    void onConnectionError();

}
