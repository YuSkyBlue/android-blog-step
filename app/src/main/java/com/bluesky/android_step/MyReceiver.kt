package com.bluesky.android_step

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.bluesky.android_step.MainActivity.Companion.ANY
import com.bluesky.android_step.MainActivity.Companion.WIFI
import com.bluesky.android_step.MainActivity.Companion.refreshDisplay
import com.bluesky.android_step.MainActivity.Companion.sPref

class MyReceiver : BroadcastReceiver() {

    /** 패키지명.action.액션명*/
    val MY_ACTION = "com.bluesky.android_step.ACTION_MY_BROADCAST"



    override fun onReceive(context: Context?, intent: Intent?) {
        val conn = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        when(intent?.action){
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                if (WIFI == sPref && networkInfo?.type == ConnectivityManager.TYPE_WIFI) {
                    // If device has its Wi-Fi connection, sets refreshDisplay
                    // to true. This causes the display to be refreshed when the user
                    // returns to the app.
                    refreshDisplay = true
                    Toast.makeText(context, "Wifi is Connected", Toast.LENGTH_SHORT).show()

                    // If the setting is ANY network and there is a network connection
                    // (which by process of elimination would be mobile), sets refreshDisplay to true.
                } else if (ANY == sPref && networkInfo != null) {
                    refreshDisplay = true

                    // Otherwise, the app can't download content--either because there is no network
                    // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                    // is no Wi-Fi connection.
                    // Sets refreshDisplay to false.
                } else {
                    refreshDisplay = false
                    Toast.makeText(context, "Wifi is Disconnected", Toast.LENGTH_SHORT).show()
                }
            }
            MY_ACTION -> {
                Toast.makeText(context, "Custom Broadcast Received", Toast.LENGTH_SHORT).show()
            }
        }



    }
}