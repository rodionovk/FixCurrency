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
        if(pos == -1)
            return;

        items.add(0, items.get(pos));
        items.remove(pos + 1);
        notifyItemMoved(pos, 0);
    }

    void updateRates(String newValue, String name) {
        if(TextUtils.isEmpty(newValue) || newValue.equals("0") || newValue.equals("0.")) {

            for(Rate rate : items) {
                rate.setValue("");
                notifyItemChanged(items.indexOf(rate));
            }
        }
        else {

            Double dNewValue = Double.parseDouble(newValue);
            Double dRelateToBase = Double.parseDouble(currencies.getMapRates().get(name));

            for(int i = 0; i < items.size(); i++) {

                Rate rate = items.get(i);

                Double dRelateCurentToBase = Double.parseDouble(currencies.getMapRates().get(rate.getName()));
                Double koef = dRelateCurentToBase/dRelateToBase;
                Double result = koef * dNewValue;

                String sResult = String.format(Locale.US, "%.4f", result);

                rate.setValue(sResult);

                if(i != 0)
                    notifyItemChanged(i);

                currencies.getMapRates().put(rate.getName(), sResult);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            i++;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            i++;
        }

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
