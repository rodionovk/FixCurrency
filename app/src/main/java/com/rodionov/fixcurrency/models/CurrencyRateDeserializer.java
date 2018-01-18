package com.rodionov.fixcurrency.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public class CurrencyRateDeserializer implements JsonDeserializer<Currencies> {

    private static final String KEY_BASE = "base";
    private static final String KEY_RATES = "rates";

    @Override
    public Currencies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final String base = jsonObject.get(KEY_BASE).getAsString();

        final List<Rate> rates = readRatesMap(jsonObject);
        rates.add(0, new Rate(base, "1"));

        Currencies currencies = new Currencies();
        currencies.setBase(base);
        currencies.setRates(rates);

        return currencies;
    }

    @Nullable
    private List<Rate> readRatesMap(@NonNull final JsonObject jsonObject) {
        final JsonElement ratesElement = jsonObject.get(KEY_RATES);
        if(ratesElement == null) {
            return null;
        }

        final JsonObject rateObject = ratesElement.getAsJsonObject();
        final List<Rate> rates = new ArrayList<>();

        for(Map.Entry<String, JsonElement> entry : rateObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            rates.add(new Rate(key, value));
        }

        return rates;
    }
}
