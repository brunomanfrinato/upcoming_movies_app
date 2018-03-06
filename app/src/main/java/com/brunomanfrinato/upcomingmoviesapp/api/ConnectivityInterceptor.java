package com.brunomanfrinato.upcomingmoviesapp.api;

import android.content.Context;

import com.brunomanfrinato.upcomingmoviesapp.exception.NoConnectivityException;
import com.brunomanfrinato.upcomingmoviesapp.util.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context context;

    ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Utils.isOnline(context)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
