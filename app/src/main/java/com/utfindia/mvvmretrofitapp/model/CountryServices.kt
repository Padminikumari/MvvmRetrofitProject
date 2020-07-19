package com.utfindia.mvvmretrofitapp.model


import com.utfindia.mvvmretrofitapp.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountryServices private constructor() {
    @JvmField
    @Inject
    var api: CountriesApi? = null

    fun getAllPhotos(method: String?, api_key: String?, text: String?, format: String?, nojsoncallback: Int, per_page: Int, page: Int): Single<Photos?>? {
        return api!!.getAllPhotos(method, api_key, text, format, nojsoncallback, per_page, page)
    }

    companion object {
        @JvmStatic
        var instance: CountryServices? = null
            get() {
                if (field == null) {
                    field = CountryServices()
                }
                return field
            }
            private set
    }

    init {
        DaggerApiComponent.create().inject(this)

    }
}