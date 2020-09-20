package com.example.myapplication.sdk

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.view.*

public class Utilities {

    companion object {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        public fun getInstalledApplications(
            context:Context
           ): ArrayList<PackageInfoClass> {

            var mainIntent = Intent(Intent.ACTION_MAIN,null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            var pkgList :List<ResolveInfo>  = context.packageManager.queryIntentActivities(mainIntent,0)
            var packageInfoList: ArrayList<PackageInfoClass> = ArrayList<PackageInfoClass>()

            for(element in pkgList){
                var pkgInfo = element
                var pkgDetail = context.packageManager.getPackageInfo(pkgInfo.activityInfo.packageName,0)
                var packageInfoObj = PackageInfoClass(
                    pkgInfo.activityInfo.loadLabel(context.packageManager) as String,
                    pkgInfo.activityInfo.packageName,
                    pkgDetail.versionName,
                    pkgDetail.versionCode,
                    pkgDetail.applicationInfo.loadIcon(context.packageManager)
                )
                packageInfoList.add(packageInfoObj)
            }
            return packageInfoList
        }

        private fun isSystemPackage(pkgInfo:PackageInfo): Boolean {

            return (pkgInfo.applicationInfo.flags != 0 && ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0)
            //return (pkgInfo.c)
        }
    }
}