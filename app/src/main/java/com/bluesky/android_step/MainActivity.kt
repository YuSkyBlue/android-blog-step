package com.bluesky.android_step

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject  lateinit var userRepository: UserRepository
    @Inject  lateinit var userDataSource: UserDataSource
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val textView1 = findViewById<TextView>(R.id.textView1).also {
             it.text = userRepository.getUserData()
         }
        val textView2 = findViewById<TextView>(R.id.textView2).also {
            it.text =   userDataSource.getUserData()
        }

    }
}