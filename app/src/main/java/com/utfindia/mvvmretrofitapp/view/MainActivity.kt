package com.utfindia.mvvmretrofitapp.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.utfindia.mvvmretrofitapp.R
import com.utfindia.mvvmretrofitapp.model.Photo
import com.utfindia.mvvmretrofitapp.model.Photos
import com.utfindia.mvvmretrofitapp.viewmodel.ListViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private var listcountry: RecyclerView? = null
    private var manager: LinearLayoutManager? = null
    private var errorlist: TextView? = null
    private var loading: ProgressBar? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var listViewModel: ListViewModel? = null
    private var photoList: List<Photo>? = null
    private val searchIcon: EditText? = null
    private val photosAdapter = PhotosAdapter(ArrayList())
    private var currentItes = 0
    private var totalItems = 0
    private var scrolloutItem = 0
    private var previoustotal = 0
    private val view_threshold = 10
    var isScrolled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listcountry = findViewById(R.id.countrylist)
        errorlist = findViewById(R.id.error_list)
        loading = findViewById(R.id.loading_view)
        swipeRefreshLayout = findViewById(R.id.refreslayout)
        photoList = ArrayList()
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        manager = LinearLayoutManager(this)
        listViewModel!!.refreshPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360", searchType, "json", 1, par_page, pagecount)
        listcountry?.setLayoutManager(manager)
        listcountry?.setAdapter(photosAdapter)
        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            pagecount = 1
            swipeRefreshLayout?.setRefreshing(false)
        })
        observeViewModelPhotos()
        listcountry?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolled = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItes = manager!!.childCount
                totalItems = manager!!.itemCount
                scrolloutItem = manager!!.findFirstVisibleItemPosition()
                Log.e(TAG, "C_T  = $currentItes S_I = $scrolloutItem T_I = $totalItems")
                if (isScrolled && currentItes + scrolloutItem == totalItems) {
                    Log.e(TAG, "C_T!  = $currentItes S_I = $scrolloutItem T_I = $totalItems")
                    isScrolled = false
                    previoustotal = totalData - totalItems
                    if (previoustotal > 10) {
                        par_page += 10
                        listViewModel!!.refreshPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360", searchType, "json", 1, par_page, pagecount)
                        observeViewModelPhotos()
                    } else {
                        par_page += previoustotal
                        listViewModel!!.refreshPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360", searchType, "json", 1, par_page, pagecount)
                        observeViewModelPhotos()
                    }
                }
            }
        })
    }

    private fun observeViewModelPhotos() {
        listViewModel!!.photosMutableLiveData.observe(this, Observer { photos: Photos? ->
            if (photos != null) {
                if ((photos.stat == "ok")) {
                    listcountry!!.visibility = View.VISIBLE
                    photoList = photos.photos?.photo
                    totalData = photos.photos?.total!!.toInt()
                    photoList?.let { photosAdapter.updateCountryList(it) }
                } else {
                    val alertDialog = AlertDialog.Builder(this).create()
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage(photos.message)
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    alertDialog.show()
                }
            }
        })
        listViewModel!!.countryLoadError.observe(this, Observer { isError: Boolean? ->
            if (isError != null) {
                errorlist!!.visibility = if (isError) View.VISIBLE else View.INVISIBLE
            }
        })
        listViewModel!!.loading.observe(this, Observer { isLoading: Boolean? ->
            if (isLoading != null) {
                loading!!.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
                if (isLoading) {
                    listcountry!!.visibility = View.GONE
                    errorlist!!.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.main_search_icon)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length >= 3) {
                    searchType = ""
                    searchType = query
                    Log.e("SearchType", searchType + "")
                    listViewModel!!.refreshPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360", searchType, "json", 1, 10, 1)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length >= 3) {
                    searchType = ""
                    searchType = newText
                    Log.e("SearchType", searchType + "")
                    listViewModel!!.refreshPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360", searchType, "json", 1, 10, 1)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val TAG = "MainActivity"
        private var searchType = "photos"
        private var pagecount = 1
        private var totalData = 0
        private var par_page = 10
    }
}