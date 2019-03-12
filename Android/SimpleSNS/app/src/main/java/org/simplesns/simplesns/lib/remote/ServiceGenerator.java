package org.simplesns.simplesns.lib.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simplesns.simplesns.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chanj on 17/02/2018.
 */

public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit.create(serviceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}