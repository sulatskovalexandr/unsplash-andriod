package com.example.myapplication.common

import android.content.Context
import android.net.ConnectivityManager

class NetworkChecker(context: Context) {
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkConnected(): Boolean {
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}