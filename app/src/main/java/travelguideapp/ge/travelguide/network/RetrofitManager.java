package travelguideapp.ge.travelguide.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;
import travelguideapp.ge.travelguide.network.api.ReportApi;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

public class RetrofitManager {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.e("http_interceptor", message));

        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(chain -> {
                        final Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", GlobalPreferences.getAccessToken())
                                .build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(200, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS)
                    .writeTimeout(200, TimeUnit.SECONDS)
                    .callTimeout(200, TimeUnit.SECONDS)
                    .protocols(Util.immutableListOf(Protocol.HTTP_1_1))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


    public static ApiService getApiService() {
        return getRetrofit().create(ApiService.class);
    }

    public static ReportApi getReportApi() {
        return getRetrofit().create(ReportApi.class);
    }

    public static AuthorizationApi getAuthorizationApi() {
        return getRetrofit().create(AuthorizationApi.class);
    }

}

