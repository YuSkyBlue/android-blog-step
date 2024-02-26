package com.bluesky.android_step

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*

sealed class LiveDataResult<out T> {
    data class Success<T>(val data: T) : LiveDataResult<T>()
    object Timeout : LiveDataResult<Nothing>()
}
fun <T> LiveData<T>.observeOnceWithTimeout(timeout: Int = 3000, observer: Observer<LiveDataResult<T>>) {
    var coroutineClose = false
    var isTimeout = false

    val changer = object : Observer<T> {
        override fun onChanged(t: T) {
            if (t == null && !isTimeout)
                return

            coroutineClose = true
            observer.onChanged(LiveDataResult.Success(t))

            removeObserver(this)
        }
    }

    observeForever(changer)

    CoroutineScope(Dispatchers.Default).launch {
        var current = 0

        while (current < timeout) {
            delay(100)
            current += 100

            if (coroutineClose)
                break
        }

        if (current >= timeout)
        {
            withContext(Dispatchers.Main)
            {
                isTimeout = true
                observer.onChanged(LiveDataResult.Timeout)
                removeObserver(changer)
            }
        }
    }
}