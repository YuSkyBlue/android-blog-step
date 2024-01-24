package com.bluesky.android_step.step6

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bluesky.android_step.BaseActivity
import com.bluesky.android_step.R
import com.bluesky.android_step.databinding.ActivityStep6Binding
import dagger.hilt.android.AndroidEntryPoint
import com.bluesky.android_step.step5.repeatOnStarted
import com.bluesky.android_step.step6.Step6ViewModel.Event

@AndroidEntryPoint
class Step6Activity :
    BaseActivity<ActivityStep6Binding, Step6ViewModel>(R.layout.activity_step6) {

    override val viewModel: Step6ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
