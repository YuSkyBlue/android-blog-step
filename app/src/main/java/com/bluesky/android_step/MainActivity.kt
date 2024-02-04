package com.bluesky.android_step

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluesky.android_step.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mReceiver : BroadcastReceiver = MyReceiver()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendBroadcast.setOnClickListener {
            /** Custom BroadCast*/
            val intent = Intent(MyReceiver().MY_ACTION)
            sendBroadcast(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(MyReceiver().MY_ACTION)
        registerReceiver(mReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }
}