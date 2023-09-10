package com.example.myapplication.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

private val networkReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        if (extras != null) {
            val networkInfo = extras.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
            if (networkInfo.state == NetworkInfo.State.CONNECTED) {
//
            }
        }
    }
}