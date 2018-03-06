package com.brunomanfrinato.upcomingmoviesapp.api;

import android.content.Context;

import com.brunomanfrinato.upcomingmoviesapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static Retrofit getRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new KeyInterceptor());
        okBuilder.addInterceptor(new ConnectivityInterceptor(context));
        okBuilder.addInterceptor(logging);

        OkHttpClient okHttpClient = okBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

}
