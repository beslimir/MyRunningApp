package com.beslimir.myrunningapp.presentation.main_screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentStatisticsBinding
import com.beslimir.myrunningapp.other.CustomMarkerView
import com.beslimir.myrunningapp.presentation.statistics.StatisticsViewModel
import com.beslimir.myrunningapp.util.TrackingUtility
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToLong

@AndroidEntryPoint
class StatisticsFragment: Fragment(R.layout.fragment_statistics) {

    lateinit var statisticsBinding: FragmentStatisticsBinding
    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statisticsBinding = FragmentStatisticsBinding.bind(view)

        subscribeToObservers()
        setupBarChart()
    }

    private fun setupBarChart() {
        statisticsBinding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.apply {
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
    }

    private fun subscribeToObservers() {
        statisticsViewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                statisticsBinding.tvTotalTime.text = totalTimeRun
            }
        })
        statisticsViewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000f
                val totalDistance = (km * 10f).roundToLong() / 10f
                val totalDistanceString = "${totalDistance}km"
                statisticsBinding.tvTotalDistance.text = totalDistanceString
            }
        })
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = (it * 10f).roundToLong() / 10f
                val avgSpeedString = "${avgSpeed}km/h"
                statisticsBinding.tvAverageSpeed.text = avgSpeedString
            }
        })
        statisticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "${it}kcal"
                statisticsBinding.tvTotalCalories.text = totalCalories
            }
        })
        statisticsViewModel.runsSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAvgSpeeds = it.indices.map { i ->
                    BarEntry(i.toFloat(), it[i].avgSpeedInKMH)
                }
                val barDataSet = BarDataSet(allAvgSpeeds, "Avg Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                statisticsBinding.barChart.data = BarData(barDataSet)
                statisticsBinding.barChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                statisticsBinding.barChart.invalidate()
            }
        })
    }

}