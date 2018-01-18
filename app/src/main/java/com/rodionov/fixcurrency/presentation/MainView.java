package com.rodionov.fixcurrency.presentation;

import com.rodionov.fixcurrency.models.Currencies;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public interface MainView {

    void showLoading(boolean show);
    void showError();
    void showRateCurrencies(Currencies currencies);
}
