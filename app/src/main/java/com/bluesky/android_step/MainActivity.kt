package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var backgroundThread: Thread

    // Define what your message will contain. In this case, we're just sending a simple string.
    companion object {
        const val UPDATE_STATUS = 1
    }

    // Handler associated with the main thread's Looper to handle messages
    private val uiHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                UPDATE_STATUS -> {
                    val text = msg.obj as String
                    statusTextView.text = text
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusTextView = findViewById(R.id.statusTextView)

        startBackgroundTask()
    }

    private fun startBackgroundTask() {
        backgroundThread = Thread {
            for (i in 1..10) {
                // Simulate a task
                Thread.sleep(1000) // simulate a delay

                // Send a message to the Handler
                val message = Message.obtain().apply {
                    what = UPDATE_STATUS
                    obj = "Update $i"
                }
                uiHandler.sendMessage(message)
            }
        }
        backgroundThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundThread.interrupt()
    }
}
