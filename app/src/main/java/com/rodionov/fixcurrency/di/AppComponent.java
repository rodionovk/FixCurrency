package com.rodionov.fixcurrency.di;

import com.rodionov.fixcurrency.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */
@Component(modules = {AppModule.class, RetrofitModule.class, CurrenciesModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
