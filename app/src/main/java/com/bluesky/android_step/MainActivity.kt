package com.bluesky.android_step

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bluesky.android_step.step1.Step1Activity
import com.bluesky.android_step.step2.Step2Activity
import com.bluesky.android_step.step3.Step3Activity
import com.bluesky.android_step.step4.Step4Activity
import com.bluesky.android_step.step5.Step5Activity
import com.bluesky.android_step.step6.Step6Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_step1).setOnClickListener {
            startActivity(Step1Activity::class.java)
        }
        findViewById<Button>(R.id.btn_step2).setOnClickListener {
            startActivity(Step2Activity::class.java)
        }
        findViewById<Button>(R.id.btn_step3).setOnClickListener {
            startActivity(Step3Activity::class.java)
        }
        findViewById<Button>(R.id.btn_step4).setOnClickListener {
            startActivity(Step4Activity::class.java)
        }
        findViewById<Button>(R.id.btn_step5).setOnClickListener {
            startActivity(Step5Activity::class.java)
        }
        findViewById<Button>(R.id.btn_step6).setOnClickListener {
            startActivity(Step6Activity::class.java)
        }
    }
    private fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}