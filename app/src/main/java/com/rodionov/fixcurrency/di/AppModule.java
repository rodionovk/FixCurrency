package com.rodionov.fixcurrency.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

@Module
@Singleton
public class AppModule {

    private Context context;

    public AppModule(Context aContext) {
        context = aContext.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return context;
    }
}
