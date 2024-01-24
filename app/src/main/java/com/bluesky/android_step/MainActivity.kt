package com.bluesky.android_step

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.bluesky.android_step.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.layout01.root.visibility = View.INVISIBLE
        binding.layout02.root.visibility = View.VISIBLE

        binding.layout02.button2.setOnClickListener {
//            animateVisibility(binding.layout02.root, View.INVISIBLE)
//            animateVisibility(binding.layout01.root, View.VISIBLE)
            animateTranslation(
                binding.layout02.root,
                0f,
                -binding.layout02.root.width.toFloat(),
                View.INVISIBLE
            )
            animateTranslation(
                binding.layout01.root,
                binding.layout01.root.width.toFloat(),
                0f,
                View.VISIBLE
            )
            binding.layout01.textView01.text = "hello world, I'm back"
        }
        binding.layout01.button1.setOnClickListener {
//            animateVisibility(binding.layout02.root, View.INVISIBLE)
//            animateVisibility(binding.layout01.root, View.VISIBLE)
            animateTranslation(
                binding.layout01.root,
                0f,
                -binding.layout01.root.width.toFloat(),
                View.INVISIBLE
            )
            animateTranslation(
                binding.layout02.root,
                binding.layout02.root.width.toFloat(),
                0f,
                View.VISIBLE
            )
            binding.layout02.textView2.text = "Change Value Haha"
        }
    }

    private fun animateTranslation(view: View, fromX: Float, toX: Float, visibility: Int) {
        val translation = TranslateAnimation(fromX, toX, 0f, 0f)
        translation.duration = 500 // Adjust the duration as needed
        translation.fillAfter = false
        translation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = visibility
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view.startAnimation(translation)
    }
}

//    private fun animateVisibility(view: View, visibility: Int) {
//        val fadeInOut = ObjectAnimator.ofFloat(view, "alpha", if (visibility == View.VISIBLE) 1f else 0f)
//        fadeInOut.duration = 500 // Adjust the duration as needed
//        fadeInOut.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                view.visibility = visibility
//            }
//        })
//        fadeInOut.start()
//    }