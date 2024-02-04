package com.bluesky.android_step

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    /** 패키지명.action.액션명*/
     val MY_ACTION = "com.bluesky.android_step.ACTION_MY_BROADCAST"
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show()
            }
            MY_ACTION -> {
                Toast.makeText(context, "Custom Broadcast Received", Toast.LENGTH_SHORT).show()
            }
        }
    }
}