package com.beslimir.myrunningapp.presentation.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentStatisticsBinding
import com.beslimir.myrunningapp.util.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    lateinit var statisticsBinding: FragmentStatisticsBinding
    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statisticsBinding = FragmentStatisticsBinding.bind(view)

        subscribeToObservers()
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
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                statisticsBinding.tvTotalDistance.text = totalDistanceString
            }
        })
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
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
    }

}