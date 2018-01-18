package com.rodionov.fixcurrency.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodionov.fixcurrency.BuildConfig;
import com.rodionov.fixcurrency.data.FixApiService;
import com.rodionov.fixcurrency.models.Currencies;
import com.rodionov.fixcurrency.models.CurrencyRateDeserializer;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

@Module
@Singleton
public class RetrofitModule {

    private static final int TIMEOUT = 20;

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Converter.Factory factory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Currencies.class, new CurrencyRateDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    FixApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(FixApiService.class);
    }
}
