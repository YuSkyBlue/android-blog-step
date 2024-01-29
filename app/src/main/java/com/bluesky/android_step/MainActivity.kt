package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bluesky.android_step.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val TAG = "ManActivity"
    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.frameLayout)
    private var doubleBackToExit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            fragment1Btn.setOnClickListener {
                setFragment2(Fragment1())
            }
            fragment2Btn.setOnClickListener {
                setFragment2(Fragment2())
            }
            fragment3Btn.setOnClickListener {
                fragmentBackStack()
            }
        }

    }

    private fun setFragment(frag: Fragment) {  //2번
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    private fun setFragment2(fragment: Fragment) {
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(" ")
                .commit()
        }
    }

    private fun fragmentBackStack() {
        currentFragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commitNow()
        }
        try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (supportFragmentManager.backStackEntryCount == 0) {
            Log.e(TAG, "backStackEntryCount == 0")
            finish()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount != 0){
            super.onBackPressed()
        } else {
            if (doubleBackToExit) {
                finishAffinity()
            } else {
                Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                doubleBackToExit = true
                runDelayed(1500L) {
                    doubleBackToExit = false
                }
            }
        }

    }

    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}