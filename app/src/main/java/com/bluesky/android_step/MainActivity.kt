package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var textView: TextView
    private val handler = Handler()
    private var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Find the button and text view by their IDs
        button1= findViewById<Button>(R.id.button1)
        button2 = findViewById<Button>(R.id.button2)
        textView = findViewById<TextView>(R.id.textview)


        // Set click listeners for Button 1 and Button 2
        button1.setOnClickListener {
            textView.text = "Button 1 clicked $count"
        }

        button2.setOnClickListener {
            textView.text = "Button 2 clicked $count"
        }


        // Schedule a repeated task using Handler to find closest button every 3 seconds
        handler.postDelayed(object : Runnable {
            override fun run() {
                count++
                // Generate random coordinates
                val randomX = Random.nextInt(0, 1200) // Set your desired range
                val randomY = Random.nextInt(0, 1920) // Set your desired range
                findClosestButton(randomX,randomY)
                handler.postDelayed(this, 3000)
            }
        }, 3000)

    }
    // Function to find the closest button based on random coordinates

    private fun findClosestButton(randomX: Int, randomY: Int) {
        Log.d("MainActivity", "randonX :  $randomX, randomY : $randomY")
        // Get the screen coordinates of Button 1
        val location1 = IntArray(2)
        button1.getLocationOnScreen(location1)
        // Calculate the distance between random coordinates and Button 1
        val distanceToButton1 = calculateDistance(randomX, randomY, location1[0], location1[1])

        // Get the screen coordinates of Button 2
        val location2 = IntArray(2)
        button2.getLocationOnScreen(location2)
        // Calculate the distance between random coordinates and Button 2
        val distanceToButton2 = calculateDistance(randomX, randomY, location2[0], location2[1])

        // Trigger a touch event on the closest button
        if (distanceToButton1 < distanceToButton2) {
            simulateTouchEvent(location1[0], location1[1])
        } else {
            simulateTouchEvent(location2[0], location2[1])
        }
    }
    // Function to calculate the distance between two points in 2D space
    private fun calculateDistance(x1: Int, y1: Int, x2: Int, y2: Int): Float {
        return hypot((x2 - x1).toDouble(), (y2 - y1).toDouble()).toFloat()
    }
    // Function to simulate a touch event at the specified coordinates
    private fun simulateTouchEvent(x: Int, y: Int) {
        val motionEvent = MotionEvent.obtain(
            0,
            0,
            MotionEvent.ACTION_DOWN,
            x.toFloat(),
            y.toFloat(),
            0
        )

        dispatchTouchEvent(motionEvent)

        motionEvent.action = MotionEvent.ACTION_UP
        dispatchTouchEvent(motionEvent)

        motionEvent.recycle()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_UP -> {
                Log.d("MainActivity", "Touch Down Event x: ${event.x}, rawX : ${event.rawX}")
                Log.d("MainActivity", "Touch Down Event y: ${event.y}, rawY : ${event.rawY}")
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d("MainActivity", "Touch Down Event x: ${event.x}, rawX : ${event.rawX}")
                Log.d("MainActivity", "Touch Down Event y: ${event.y}, rawY : ${event.rawY}")
            }
        }
        return super.onTouchEvent(event)
    }
}