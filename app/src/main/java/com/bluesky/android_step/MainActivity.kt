package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Find the button and text view by their IDs
        val button1= findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val textView = findViewById<TextView>(R.id.textview)
        // x :  600 , y 1360


//
//        // Optionally, update the text view with the coordinates
        button1.setOnClickListener {
            textView.text = "Touch event simulated on button1"
        }
        button2.setOnClickListener {
            val location = IntArray(2)
            button1.getLocationOnScreen(location)
            val currentX = location[0]
            val currentY = location[1]
            simulateTouchEvent(currentX,currentY)
        }
    }

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