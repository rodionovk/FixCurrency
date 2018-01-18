package com.rodionov.fixcurrency.repositories;

import com.rodionov.fixcurrency.data.FixApiService;
import com.rodionov.fixcurrency.models.Currencies;

import io.reactivex.Single;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class FixRepository {

    private FixApiService apiService;

    public FixRepository(FixApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Currencies> getRateCurrenciesFromApi() {
        return apiService.getRateCurrencies();
    }
}
