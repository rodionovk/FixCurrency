package com.rodionov.fixcurrency.presentation;

import com.rodionov.fixcurrency.business.CurrenciesInteractor;
import com.rodionov.fixcurrency.models.Currencies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class MainPresenter {

    private final CurrenciesInteractor currenciesInteractor;
    private MainView mainView;

    MainPresenter(CurrenciesInteractor currenciesInteractor, MainView mainView) {
        this.currenciesInteractor = currenciesInteractor;
        this.mainView = mainView;
    }

    void init() {
        Disposable disposable = currenciesInteractor.getRateCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disp -> mainView.showLoading(true))
                .doAfterTerminate(() -> mainView.showLoading(false))
                .subscribe(this::handleResponse, throwable -> mainView.showNetworkError(true));
    }

    private void handleResponse(Currencies currencies) {
        mainView.showRateCurrencies(currencies);
    }

}
