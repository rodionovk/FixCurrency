package com.rodionov.fixcurrency.data;

import com.rodionov.fixcurrency.models.Currencies;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by rodionov on 17.01.2018. FixCurrency
 */

public interface FixApiService {

    @GET("latest")
    Single<Currencies> getRateCurrencies();
}
