package com.utfindia.mvvmretrofitapp.di

import com.utfindia.mvvmretrofitapp.model.CountriesApi
import com.utfindia.mvvmretrofitapp.model.CountryServices
import com.utfindia.mvvmretrofitapp.model.CountryServices.Companion.instance
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun providerApi(): CountriesApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi::class.java)
    }

    @Provides
    fun countryServices(): CountryServices? {
        return CountryServices.instance
    }

    companion object {
        private const val BASE_URL = "https://api.ﬂickr.com/"
    }
}