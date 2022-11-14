package com.beslimir.myrunningapp.presentation.main_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beslimir.myrunningapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private val mainViewModel: MainViewModel by viewModels()

}