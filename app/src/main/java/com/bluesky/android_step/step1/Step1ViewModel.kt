package com.bluesky.android_step.step1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluesky.android_step.step1.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Step1ViewModel @Inject constructor() : ViewModel() {

    private val _showToastEvent = MutableLiveData<Event<String>>()
    val showToastEvent: LiveData<Event<String>> = _showToastEvent

    fun showToast() {
        _showToastEvent.value = Event("토스트")
    }

}
