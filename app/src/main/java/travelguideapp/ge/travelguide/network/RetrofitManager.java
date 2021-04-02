package travelguideapp.ge.travelguide.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import travelguideapp.ge.travelguide.base.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class RetrofitManager {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.e("http_interceptor", message));

        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);

//            OkHttpClient httpClient = new OkHttpClient();
//            httpClient.networkInterceptors().add(chain -> {
//                final Request request = chain.request().newBuilder()
////                        .addHeader("Accept", "application/json")
//                        .addHeader("Authorization", BaseApplication.getAccessToken())
//                        .build();
//
//                return chain.proceed(request);
//            });

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(chain -> {
                        final Request request = chain.request().newBuilder()
//                        .addHeader("Accept", "application/json")
                                .addHeader("Authorization", BaseApplication.getAccessToken())
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
                    .baseUrl(ApiEndPoint.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofit().create(ApiService.class);
    }

}

