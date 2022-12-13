package com.beslimir.myrunningapp.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentTrackingBinding
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

        trackingBinding.mapView.getMapAsync { googleMap ->
            map = googleMap
        }
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