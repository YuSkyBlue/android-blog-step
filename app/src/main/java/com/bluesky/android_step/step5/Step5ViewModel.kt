package com.bluesky.android_step.step5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step5ViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

     fun showToast() {
        viewModelScope.launch (Dispatchers.Main){
            delay(2000L)
            event(Event.ShowToast("토스트"))
        }
    }
    fun aaa() {
        event(Event.Aaa("aaa"))
    }

    fun bbb() {
        event(Event.Bbb(36))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
        data class Aaa(val value: String) : Event()
        data class Bbb(val value: Int) : Event()
    }
}
