package com.rodionov.fixcurrency.di;

import android.content.Context;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class ComponentsManager {

    private Context context;
    private AppComponent appComponent;

    public ComponentsManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(context))
                    .build();
        }
        return appComponent;
    }
}
