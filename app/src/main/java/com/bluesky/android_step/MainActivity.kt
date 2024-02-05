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
    private lateinit var buttons: List<Button>
//    lateinit var button1: Button
//    lateinit var button2: Button
    lateinit var textView: TextView
    private val handler = Handler()
    private var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Find the button and text view by their IDs
//        button1= findViewById<Button>(R.id.button1)
//        button2 = findViewById<Button>(R.id.button2)
        textView = findViewById<TextView>(R.id.textview)
        // x :  600 , y 1360

        buttons = List(10) { index ->
            findViewById<Button>(resources.getIdentifier("button${index + 1}", "id", packageName))
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                textView.text = "Button ${index + 1} clicked $count"
            }
        }
        handler.postDelayed(object : Runnable {
            override fun run() {
                count++
                val randomX = Random.nextInt(0, 1200) // Set your desired range
                val randomY = Random.nextInt(0, 1920) // Set your desired range
                findClosestButton(randomX,randomY)
                handler.postDelayed(this, 3000)
            }
        }, 3000)

    }
    private fun findClosestButton(randomX: Int, randomY: Int) {
        Log.d("MainActivity", "randonX :  $randomX, randomY : $randomY")
        val distances = buttons.map { button ->
            val location = IntArray(2)
            button.getLocationOnScreen(location)
            val distance =calculateDistance(randomX, randomY, location[0], location[1])
            Log.d("MainActivity", "Distance to Button : $distance")
            distance
        }

        val closestButtonIndex = distances.indexOf(distances.minOrNull())
        simulateTouchEvent(buttons[closestButtonIndex])
//        if (distanceToButton1 < distanceToButton2) {
//            simulateTouchEvent(location1[0], location1[1])
//        } else {
//            simulateTouchEvent(location2[0], location2[1])
//        }
    }
    private fun calculateDistance(x1: Int, y1: Int, x2: Int, y2: Int): Float {
        return hypot((x2 - x1).toDouble(), (y2 - y1).toDouble()).toFloat()
    }
    private fun simulateTouchEvent(button: Button) {
        val location = IntArray(2)
        button.getLocationOnScreen(location)

        val motionEvent = MotionEvent.obtain(
            0,
            0,
            MotionEvent.ACTION_DOWN,
            location[0].toFloat(),
            location[1].toFloat(),
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