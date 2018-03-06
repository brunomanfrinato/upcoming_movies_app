package com.brunomanfrinato.upcomingmoviesapp.api;


import com.brunomanfrinato.upcomingmoviesapp.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class KeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();

        HttpUrl newUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("language", "en-US")
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(newUrl);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}
