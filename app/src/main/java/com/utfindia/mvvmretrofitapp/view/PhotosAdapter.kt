package com.utfindia.mvvmretrofitapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utfindia.mvvmretrofitapp.R
import com.utfindia.mvvmretrofitapp.model.Photo
import com.utfindia.mvvmretrofitapp.view.PhotosAdapter.DemoHolder

class PhotosAdapter(private val list: MutableList<Photo>) : RecyclerView.Adapter<DemoHolder>() {
    fun updateCountryList(updatelist: List<Photo>) {
        list.clear()
        list.addAll(updatelist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DemoHolder(view)
    }

    override fun onBindViewHolder(holder: DemoHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class DemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView
        private val title: TextView
        fun bind(photo: Photo) {
            title.text = photo.title
            val url = "https://farm" + photo.farm.toString() + ".staticflickr.com/" + photo.server.toString() + "/" + photo.id.toString() + "_" + photo.secret.toString() + "_m.jpg"
            Util.loadImage(poster, url, Util.setProgress(poster.context))
        }

        init {
            title = itemView.findViewById(R.id.card_title)
            poster = itemView.findViewById(R.id.imageView)
        }
    }

}