package com.bluesky.android_step.step1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bluesky.android_step.BaseActivity
import com.bluesky.android_step.R
import com.bluesky.android_step.databinding.ActivityStep1Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Step1Activity :
    BaseActivity<ActivityStep1Binding, Step1ViewModel>(R.layout.activity_step1) {

    override val viewModel: Step1ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.showToastEvent.observe(this, { event ->
            event.getContentIfNotHandled()?.let { text ->
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
