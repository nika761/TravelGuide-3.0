package travelguideapp.ge.travelguide.base;

public interface BaseListener {

    void onError(String message);

    void onAuthenticationError(String message);

    void onConnectionError();

}
