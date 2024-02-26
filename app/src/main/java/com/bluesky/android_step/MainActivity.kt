package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val liveDataSample = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveDataSample.observeOnceWithTimeout(timeout = 5000, observer = Observer { value ->
            value?.let {
                println("Received LiveData value: $it")
                Log.d("MainActivity","Received LiveData value: $it" )
            }
        })

        // Simulate delay before setting a value
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // Delay for 3 seconds
            Log.d("MainActivity","Hello, LiveData!" )

            liveDataSample.value = "Hello, LiveData!"
        }
    }
}