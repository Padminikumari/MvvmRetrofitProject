package com.utfindia.mvvmretrofitapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photos {
    @SerializedName("photos")
    @Expose
    var photos: Photos_? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null

    @SerializedName("code")
    @Expose
    var code = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

}