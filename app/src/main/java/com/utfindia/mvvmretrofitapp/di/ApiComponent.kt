package com.utfindia.mvvmretrofitapp.di

import com.utfindia.mvvmretrofitapp.di.ApiModule
import com.utfindia.mvvmretrofitapp.model.CountryServices
import com.utfindia.mvvmretrofitapp.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(services: CountryServices?)
    fun injectMVClass(listViewModel: ListViewModel?)
}