package com.rodionov.fixcurrency.di;

import com.rodionov.fixcurrency.business.CurrenciesInteractor;
import com.rodionov.fixcurrency.data.FixApiService;
import com.rodionov.fixcurrency.repositories.FixRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

@Module
@Singleton
public class CurrenciesModule {

    @Provides
    @Singleton
    CurrenciesInteractor provideInteractor(FixRepository repository) {
        return new CurrenciesInteractor(repository);
    }

    @Provides
    @Singleton
    FixRepository provideRepository(FixApiService apiService) {
        return new FixRepository(apiService);
    }
}
