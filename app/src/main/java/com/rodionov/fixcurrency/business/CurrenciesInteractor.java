package com.rodionov.fixcurrency.business;

import com.rodionov.fixcurrency.models.Currencies;
import com.rodionov.fixcurrency.repositories.FixRepository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class CurrenciesInteractor {

    private FixRepository fixRepository;

    public CurrenciesInteractor(FixRepository fixRepository) {
        this.fixRepository = fixRepository;
    }

    public Single<Currencies> getRateCurrencies() {
        return fixRepository.getRateCurrenciesFromApi()
                .subscribeOn(Schedulers.io());
    }
}
