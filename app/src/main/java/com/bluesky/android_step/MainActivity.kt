package com.bluesky.android_step

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.bluesky.android_step.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private val mReceiver : BroadcastReceiver = MyReceiver()
    private lateinit var mReceiver: MyReceiver
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mReceiver = MyReceiver()
        binding.sendBroadcast.setOnClickListener {
            /** Custom BroadCast*/
            val intent = Intent(MyReceiver().MY_ACTION)
            sendBroadcast(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        sPref = sharedPrefs.getString("listPref", "Wi-Fi")
        updateConnectedFlags()
        if (refreshDisplay) {
            loadPage()
        }
    }
    fun loadPage() {
//        if (sPref == ANY && (wifiConnected || mobileConnected) || sPref == WIFI && wifiConnected) {
//            // AsyncTask subclass
//            DownloadXmlTask().execute(URL)
//        } else {
//            showErrorPage()
//        }
    }
    private fun updateConnectedFlags() {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeInfo: NetworkInfo? = connMgr.activeNetworkInfo
        if (activeInfo?.isConnected == true) {
            wifiConnected = activeInfo.type == ConnectivityManager.TYPE_WIFI
            mobileConnected = activeInfo.type == ConnectivityManager.TYPE_MOBILE
        } else {
            wifiConnected = false
            mobileConnected = false
        }
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction(MyReceiver().MY_ACTION)
        registerReceiver(mReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
    companion object {
        const val WIFI = "Wi-Fi"
        const val ANY = "Any"
        const val SO_URL = "http://stackoverflow.com/feeds/tag?tagnames=android&sort;=newest"

        // Whether there is a Wi-Fi connection.
        private var wifiConnected = false

        // Whether there is a mobile connection.
        private var mobileConnected = false

        // Whether the display should be refreshed.
        var refreshDisplay = true

        // The user's current network preference setting.
        var sPref: String? = null
    }
}