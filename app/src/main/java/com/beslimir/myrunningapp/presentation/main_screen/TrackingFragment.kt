package com.beslimir.myrunningapp.presentation.main_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentTrackingBinding
import com.beslimir.myrunningapp.other.Constants.ACTION_PAUSE_SERVICE
import com.beslimir.myrunningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.beslimir.myrunningapp.other.Constants.MAP_ZOOM
import com.beslimir.myrunningapp.other.Constants.POLYLINE_COLOR
import com.beslimir.myrunningapp.other.Constants.POLYLINE_WIDTH
import com.beslimir.myrunningapp.services.Polyline
import com.beslimir.myrunningapp.services.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var trackingBinding: FragmentTrackingBinding
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackingBinding = FragmentTrackingBinding.bind(view)
        trackingBinding.mapView.onCreate(savedInstanceState)

        trackingBinding.btnToggleRun.setOnClickListener {
            toggleRun()
        }

        trackingBinding.mapView.getMapAsync { googleMap ->
            map = googleMap
            addAllPolylines()
        }

        subscribeToObservers()
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            trackingBinding.btnToggleRun.text = "Start"
            trackingBinding.btnFinishRun.visibility = VISIBLE
        } else {
            trackingBinding.btnToggleRun.text = "Stop"
            trackingBinding.btnFinishRun.visibility = GONE
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) = Intent(requireContext(), TrackingService::class.java).also {
        it.action = action
        requireContext().startService(it)
    }

    override fun onResume() {
        super.onResume()
        trackingBinding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        trackingBinding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        trackingBinding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        trackingBinding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        trackingBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        trackingBinding.mapView.onSaveInstanceState(outState)
    }

}