package com.utfindia.mvvmretrofitapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utfindia.mvvmretrofitapp.di.DaggerApiComponent
//import com.utfindia.mvvmretrofitapp.di.DaggerApiComponent
import com.utfindia.mvvmretrofitapp.model.CountryServices
import com.utfindia.mvvmretrofitapp.model.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {
    var photosMutableLiveData = MutableLiveData<Photos>()
    var countryLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    @JvmField
    @Inject
    var countryServices: CountryServices? = null
    fun refreshPhotos(method: String, api_key: String, text: String, format: String, nojsoncallback: Int, per_page: Int, page: Int) {
        fetchAllPhotos(method, api_key, text, format, nojsoncallback, per_page, page)
    }

    private fun fetchAllPhotos(method: String, api_key: String, text: String, format: String, nojsoncallback: Int, per_page: Int, page: Int) {
        loading.value = true
        compositeDisposable.add(
                countryServices!!.getAllPhotos(method, api_key, text, format, nojsoncallback, per_page, page)
                        !!.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Photos?>() {
                            override fun onSuccess(countryModels: Photos) {
                                Log.e("photolist", countryModels.stat)
                                photosMutableLiveData.value = countryModels
                                countryLoadError.value = false
                                loading.value = false

                            }

                            override fun onError(e: Throwable) {
                                Log.e("photolist", "Error " + e.message)
                                countryLoadError.value = true
                                loading.value = false
                                e.printStackTrace()
                            }
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    init {
        DaggerApiComponent.create().injectMVClass(this)

    }
}