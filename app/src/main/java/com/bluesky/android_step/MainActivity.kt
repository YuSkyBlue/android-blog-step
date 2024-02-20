package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private val uiHandler = Handler(Looper.getMainLooper())
    private val totalTasks = 5
    private val completedTasks = AtomicInteger(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusTextView = findViewById(R.id.statusTextView)

        startBackgroundTask()
//        startMultipleBackgroundTasks()
    }
    /** Step 1 */
    private fun startBackgroundTask() {
        Thread {
            for (i in 1..10) {
                Thread.sleep(1000) // Simulates a task taking time
                val progress = i * 10
                // Update UI with progress
                uiHandler.post {
                    statusTextView.text = "Progress: $progress%"
                }
            }
            // Notify completion
            uiHandler.post {
                statusTextView.text = "Task Completed!"
            }
        }.start()
    }
    /** Step 2 */
    private fun startMultipleBackgroundTasks() {
        // Start multiple background threads
        for (i in 1..totalTasks) {
            Thread {
                val taskNumber = i
                // Simulate a task taking time
                val iterations = (Math.random() * 5 + 5).toInt() // Between 5 to 10 seconds
                for (j in 1..iterations) {
                    Thread.sleep(1000)
                    val progress = (j.toFloat() / iterations * 100).toInt()
                    // Update UI with task progress
                    uiHandler.post {
                        statusTextView.text = "Task $taskNumber: $progress% complete"
                    }
                }
                // Notify completion of a task
                val completed = completedTasks.incrementAndGet()
                uiHandler.post {
                    statusTextView.text = "Task $taskNumber Completed! $completed out of $totalTasks tasks done."
                    if (completed == totalTasks) {
                        statusTextView.text = "All tasks completed!"
                    }
                }
            }.start()
        }
    }
}