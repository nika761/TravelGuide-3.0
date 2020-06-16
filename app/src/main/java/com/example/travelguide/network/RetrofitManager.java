package com.example.travelguide.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static HttpLoggingInterceptor interceptor;


    private static Retrofit getRetrofit (){

        if (retrofit==null){
            interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiEndPoint.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiservice (){
        return getRetrofit().create(ApiService.class);
    }
}