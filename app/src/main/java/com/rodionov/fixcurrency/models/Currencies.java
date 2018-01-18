
package com.rodionov.fixcurrency.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Currencies {

    private String base;
    private List<Rate> rates;
    final Map<String, String> mapRates = new HashMap<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, String> getMapRates() {
        return mapRates;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;

        if(rates != null) {
            for(Rate rate : rates) {
                mapRates.put(rate.getName(), rate.getValue());
            }
        }
    }
}
