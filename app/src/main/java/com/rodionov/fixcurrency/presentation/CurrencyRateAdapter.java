package com.rodionov.fixcurrency.presentation;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rodionov.fixcurrency.R;
import com.rodionov.fixcurrency.models.Currencies;
import com.rodionov.fixcurrency.models.Rate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class CurrencyRateAdapter extends RecyclerView.Adapter<CurrencyRateAdapter.CurrencyRateViewHolder> {

    private List<Rate> items  = new ArrayList<>();
    private List<Rate> itemsCopy  = new ArrayList<>();
    private Currencies currencies;

    private OnCurrencyValueClickListener listener;

    CurrencyRateAdapter(OnCurrencyValueClickListener listener) {
        this.listener = listener;
    }

    public void setData(Currencies currencies) {
        this.currencies = currencies;

        this.items = currencies.getRates();
        this.itemsCopy.clear();
        this.itemsCopy.addAll(currencies.getRates());
        notifyDataSetChanged();
    }

    void setItemToTop(int pos) {
        items.add(0, items.get(pos));
        items.remove(pos + 1);
        notifyItemMoved(pos, 0);
    }

    void updateRates(String newValue, String name) {
        if(TextUtils.isEmpty(newValue) || newValue.equals("0")) {

            for(Rate rate : items) {
                rate.setValue("");
                notifyItemChanged(items.indexOf(rate));
            }
        }
        else {

            Double dTop = Double.parseDouble(newValue);

            String valueTop = currencies.getMapRates().get(name);
            Double dBaseTop = Double.parseDouble(valueTop);

            if(newValue.equals("0.")) {
                for(Rate rate : items) {
                    rate.setValue("");
                    notifyItemChanged(items.indexOf(rate));
                }
                return;
            }

            currencies.getMapRates().put(name, newValue);

            for(int i=1; i<items.size(); i++) {

                Rate rate = items.get(i);

                String valueCurrent = currencies.getMapRates().get(rate.getName());
                Double dCurent = Double.parseDouble(valueCurrent);
                Double koef = dCurent/dBaseTop;
                Double result = koef * dTop;

                currencies.getMapRates().put(rate.getName(), String.format(Locale.US, "%.4f", result));

                rate.setValue(String.format(Locale.US, "%.4f", result));
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public CurrencyRateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CurrencyRateViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(CurrencyRateViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CurrencyRateViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

        private TextView nameCurrency;
        private EditText valueCurrency;

        private OnCurrencyValueClickListener listener;

        CurrencyRateViewHolder(View itemView, OnCurrencyValueClickListener listener) {
            super(itemView);

            this.listener = listener;

            nameCurrency = (TextView)itemView.findViewById(R.id.name);
            valueCurrency = (EditText) itemView.findViewById(R.id.value);

            valueCurrency.setOnFocusChangeListener((view, hasFocus) -> {
                if(hasFocus)
                    listener.onValueFocused(getAdapterPosition());
            });

            valueCurrency.addTextChangedListener(this);
        }

        void bind(Rate rate) {
            nameCurrency.setText(rate.getName());
            valueCurrency.setText(rate.getValue());
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

        @Override
        public void afterTextChanged(Editable editable) {
            if(valueCurrency.hasFocus())
                listener.onCurrencyValueEdit(editable.toString(), nameCurrency.getText().toString());
        }
    }

    interface OnCurrencyValueClickListener {
        void onCurrencyValueEdit(String newValue, String name);
        void onValueFocused(int pos);
    }
}
