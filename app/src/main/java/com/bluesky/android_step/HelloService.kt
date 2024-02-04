package com.bluesky.android_step

import android.app.Service

import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.Log


class HelloService : Service() {
    private lateinit var backgroundHandler: Handler
    private lateinit var counterHandler: Handler


    private var logCounter: Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
        logCounter++

        Log.d(TAG, "Service created - Log #$logCounter")
        val handlerThread = HandlerThread("LoggingThread", THREAD_PRIORITY_BACKGROUND)
        handlerThread.start()

        backgroundHandler = Handler(handlerThread.looper)
        // Initialize a Handler for the counter with the main thread's looper
        counterHandler = Handler(Looper.getMainLooper())

        // Schedule the incrementation of logCounter every second
        counterHandler.postDelayed(counterRunnable, 1000)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")
        // Perform your background operations here

        // If the system kills the service, restart it
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")
        counterHandler.removeCallbacks(counterRunnable)
        backgroundHandler.post {
            logCounter++
            Log.d(TAG, "Continued logging after onDestroy - Log #$logCounter")
        }

    }
    private val counterRunnable = object : Runnable {
        override fun run() {
            logCounter++
            Log.d(TAG, "Incrementing logCounter - Log #$logCounter")
            // Schedule the next incrementation after 1 second
            counterHandler.postDelayed(this, 1000)

        }
    }
    companion object {
        private const val TAG = "HelloService"
    }
}