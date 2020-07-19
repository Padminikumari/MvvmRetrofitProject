package com.utfindia.mvvmretrofitapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {
    @GET("services/rest/")
    fun getAllPhotos(@Query("method") method: String?, @Query("api_key") api_key: String?, @Query("text") text: String?, @Query("format") format: String?, @Query("nojsoncallback") nojsoncallback: Int, @Query("per_page") per_page: Int, @Query("page") page: Int): Single<Photos?>?
}