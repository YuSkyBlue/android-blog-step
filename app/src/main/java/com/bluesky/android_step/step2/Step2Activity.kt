package com.bluesky.android_step.step2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bluesky.android_step.BaseActivity
import com.bluesky.android_step.R
import com.bluesky.android_step.databinding.ActivityStep2Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Step2Activity :
    BaseActivity<ActivityStep2Binding, Step2ViewModel>(R.layout.activity_step2) {

    override val viewModel: Step2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.showToastEvent.observe { text ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}
