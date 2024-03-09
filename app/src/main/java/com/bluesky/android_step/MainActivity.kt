package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bluesky.android_step.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataList = arrayListOf(
        70800.0, 70900.0, 71000.0, 71200.0, 71200.0, 70900.0,
        71000.0, 70900.0, 71100.0, 71000.0, 71200.0, 71300.0,
        71500.0, 71600.0, 71200.0, 70900.0, 70800.0, 70700.0,
        70800.0, 71000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        selectorDataValue()

    }
    private fun selectorDataValue(){
        binding.lineChart.apply {
            setOnChartValueSelectedListener(
                object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        e?.let {
                            binding.priceLiveData.text = e.y.toInt().toString()
                        }
                    }

                    override fun onNothingSelected() {

                    }
                }
            )
        }

    }
    private fun initView(){
        // Configure line chart
        binding.lineChart.apply {
            // Disable zooming
            setPinchZoom(false)
            setScaleEnabled(false)
            isDoubleTapToZoomEnabled = false

            // Disable axes, legend, and description
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false

            // Prepare data
            val entryList = arrayListOf<Entry>()
            dataList.forEachIndexed { index, d ->
                entryList.add(Entry(index.toFloat(), d.toFloat()))
            }

            // Set up line data set
            val lineDataSet = LineDataSet(entryList, "data").apply {
                // Set circle properties
                circleRadius = 1.0F
                circleHoleRadius = 1.0F
                setCircleColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                circleHoleColor = ContextCompat.getColor(this@MainActivity, R.color.red)

                // Set text properties
                valueTextSize = 11.0F
                valueTextColor = ContextCompat.getColor(this@MainActivity, R.color.red)

                // Set line properties
                lineWidth = 2.0F
                color = ContextCompat.getColor(this@MainActivity, R.color.red)
            }

            // Set up line data and refresh
            data = LineData(listOf(lineDataSet))
            invalidate()
        }
    }
}