package travelguideapp.ge.travelguide.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import travelguideapp.ge.travelguide.base.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class RetrofitManager {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.e("http_interceptor", message));

        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);

            Interceptor chain = chain1 -> {
                Request request = chain1.request().newBuilder().addHeader("Authorization", BaseApplication.getAccessToken()).build();
                return chain1.proceed(request);
            };
//
//            try {
//                interceptor.intercept((Interceptor.Chain) chain);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(200, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS)
                    .writeTimeout(200, TimeUnit.SECONDS)
                    .callTimeout(200, TimeUnit.SECONDS)
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

