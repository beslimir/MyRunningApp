package com.beslimir.myrunningapp.presentation.main_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentTrackingBinding
import com.beslimir.myrunningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.beslimir.myrunningapp.services.TrackingService
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var trackingBinding: FragmentTrackingBinding

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackingBinding = FragmentTrackingBinding.bind(view)
        trackingBinding.mapView.onCreate(savedInstanceState)

        trackingBinding.btnToggleRun.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }

        trackingBinding.mapView.getMapAsync { googleMap ->
            map = googleMap
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