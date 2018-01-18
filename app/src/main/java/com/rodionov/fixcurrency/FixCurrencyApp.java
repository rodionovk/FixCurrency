package com.rodionov.fixcurrency;

import android.app.Application;

import com.rodionov.fixcurrency.di.ComponentsManager;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class FixCurrencyApp extends Application {

    private static ComponentsManager componentsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponentsTree();
        initAppComponent();
    }

    public static ComponentsManager getComponentsManager() {
        return componentsManager;
    }

    private void initAppComponent() {
        componentsManager.getAppComponent();
    }

    private void initComponentsTree() {
        componentsManager = new ComponentsManager(this);
    }
}
