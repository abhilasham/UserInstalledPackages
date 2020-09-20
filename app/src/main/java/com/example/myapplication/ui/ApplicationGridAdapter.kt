package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.sdk.PackageInfoClass

lateinit var mClickListener : ApplicationGridAdapter.ItemClickListener

class ApplicationGridAdapter(var context: Context, var appList : ArrayList<PackageInfoClass>): RecyclerView.Adapter<AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        return AppViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appList.count()
    }

    fun getItem(position: Int): PackageInfoClass {
        return appList[position]
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.appImg?.setImageDrawable(getItem(position).icon)
        holder.appName?.text = getItem(position).appName
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

    public interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}

class AppViewHolder(var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    public var appName: TextView? = null
    public var appImg: ImageView? = null

    init {
        appName = view.findViewById(R.id.appName)
        appImg = view.findViewById(R.id.appImg)
        appImg?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (mClickListener != null) mClickListener.onItemClick(
            view,
            adapterPosition)
    }
}
