package com.utfindia.mvvmretrofitapp.view

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.utfindia.mvvmretrofitapp.R

object Util {
    fun loadImage(view: ImageView, url: String?, progressDrawable: CircularProgressDrawable?) {
        val options = RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher)
        Glide.with(view.context)
                .setDefaultRequestOptions(options)
                .load(url)
                .into(view)
    }

    fun setProgress(context: Context?): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context!!)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}