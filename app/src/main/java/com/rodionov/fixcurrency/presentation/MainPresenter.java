package com.rodionov.fixcurrency.presentation;

import com.rodionov.fixcurrency.business.CurrenciesInteractor;
import com.rodionov.fixcurrency.models.Currencies;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class MainPresenter {

    private static final int TIME_INTERVAL = 1;

    private CompositeDisposable compositeDisposable;
    private final CurrenciesInteractor currenciesInteractor;
    private MainView mainView;

    MainPresenter(CurrenciesInteractor currenciesInteractor, MainView mainView) {
        this.currenciesInteractor = currenciesInteractor;
        this.mainView = mainView;
        this.compositeDisposable = new CompositeDisposable();
    }

    void init() {

        Disposable disposable = currenciesInteractor.getRateCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disp -> mainView.showLoading(true))
                .doAfterTerminate(() -> mainView.showLoading(false))
                //.repeatWhen(objectFlowable -> objectFlowable.delay(TIME_INTERVAL, TimeUnit.SECONDS, AndroidSchedulers.mainThread()))
                .subscribe(this::handleResponse, throwable -> mainView.showNetworkError(true));

        compositeDisposable.add(disposable);
    }

    private void handleResponse(Currencies currencies) {
        mainView.showRateCurrencies(currencies);
    }

    void onDestroy() {
        mainView = null;
        compositeDisposable.clear();
    }

}
