package com.beslimir.myrunningapp.other

import android.content.Context
import android.view.LayoutInflater
import com.beslimir.myrunningapp.data.db.Run
import com.beslimir.myrunningapp.databinding.MarkerViewBinding
import com.beslimir.myrunningapp.util.TrackingUtility
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int,
) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    private lateinit var markerViewBinding: MarkerViewBinding

    init {
        markerViewBinding = MarkerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val calender = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        markerViewBinding.tvDate.text = dateFormat.format(calender.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        markerViewBinding.tvAvgSpeed.text = avgSpeed

        val distanceInKM = "${run.distanceInMeters / 1000f}km"
        markerViewBinding.tvDistance.text = distanceInKM

        markerViewBinding.tvDuration.text =
            TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        markerViewBinding.tvCaloriesBurned.text = caloriesBurned
    }

}