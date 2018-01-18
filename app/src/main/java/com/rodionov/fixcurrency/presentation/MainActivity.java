package com.rodionov.fixcurrency.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rodionov.fixcurrency.FixCurrencyApp;
import com.rodionov.fixcurrency.R;
import com.rodionov.fixcurrency.business.CurrenciesInteractor;
import com.rodionov.fixcurrency.models.Currencies;
import com.rodionov.fixcurrency.models.Rate;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView, CurrencyRateAdapter.OnCurrencyValueClickListener {

    MainPresenter mainPresenter;

    @Inject
    CurrenciesInteractor currenciesInteractor;

    private CurrencyRateAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FixCurrencyApp.getComponentsManager().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initRecyclerView();

        mainPresenter = new MainPresenter(currenciesInteractor, this);
        mainPresenter.init();
    }

    @Override
    public void showLoading(boolean show) {

        if(adapter.getItemCount() > 0 && show)
            return;

        findViewById(R.id.progressBar).setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRateCurrencies(Currencies currencies) {
        adapter.setData(currencies);
    }

    @Override
    public void onCurrencyValueEdit(Rate rate) {
        adapter.updateRates(rate);
    }

    @Override
    public void onValueFocused(int pos) {
        adapter.setItemToTop(pos);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CurrencyRateAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }
}
