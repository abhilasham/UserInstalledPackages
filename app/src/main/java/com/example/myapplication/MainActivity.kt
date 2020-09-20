package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.sdk.PackageInfoClass
import com.example.myapplication.sdk.Utilities
import com.example.myapplication.ui.ApplicationGridAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), ApplicationGridAdapter.ItemClickListener,
    SearchView.OnQueryTextListener {

    lateinit var mAdapter: ApplicationGridAdapter
    var applicationsList: ArrayList<PackageInfoClass> = ArrayList<PackageInfoClass>()
    var filteredList: ArrayList<PackageInfoClass> = ArrayList()
    lateinit var uiScope:CoroutineScope

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        searchView.queryHint = "Application name"
        searchView.isSubmitButtonEnabled = true
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

    /*     GlobalScope.launch {
           val result =
            withContext(Dispatchers.Default){

            }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            applicationsList = Utilities.getInstalledApplications(this@MainActivity)
        }
        if(applicationsList.size > 0) {
            appListView.layoutManager = GridLayoutManager(this, 4)
            mAdapter = ApplicationGridAdapter(this@MainActivity, applicationsList)
            mAdapter.setClickListener(this@MainActivity)
            appListView.adapter = mAdapter
        }
    }

    override fun onItemClick(view: View, position: Int) {
        if (filteredList.size == 0) {
            filteredList = applicationsList
        }
        var intent = packageManager.getLaunchIntentForPackage(filteredList[position].packageName)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filteredList = ArrayList<PackageInfoClass>()
        for (element in applicationsList) {
            if (element.appName.toUpperCase().contains(newText.toString().toUpperCase())) {
                filteredList.add(element)
            }
        }
        mAdapter = ApplicationGridAdapter(this, filteredList)
        appListView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
        return true
    }
}

