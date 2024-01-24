package com.bluesky.android_step.step5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bluesky.android_step.BaseActivity
import com.bluesky.android_step.R
import com.bluesky.android_step.databinding.ActivityStep5Binding
import dagger.hilt.android.AndroidEntryPoint

import com.bluesky.android_step.step5.Step5ViewModel.Event

@AndroidEntryPoint
class Step5Activity :
    BaseActivity<ActivityStep5Binding, Step5ViewModel>(R.layout.activity_step5) {

    override val viewModel: Step5ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }
        */
        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

    }

    private fun handleEvent(event: Event) = when (event) {
        is Event.ShowToast -> Toast.makeText(this, event.text, Toast.LENGTH_SHORT).show()
        is Event.Aaa -> Toast.makeText(this, "aaa event: ${event.value}", Toast.LENGTH_SHORT).show()
        is Event.Bbb -> Toast.makeText(this, "bbb event: ${event.value}", Toast.LENGTH_SHORT).show()
    }
}
