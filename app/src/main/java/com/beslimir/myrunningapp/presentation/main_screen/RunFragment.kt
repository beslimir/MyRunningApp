package com.beslimir.myrunningapp.presentation.main_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beslimir.myrunningapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment: Fragment(R.layout.fragment_run) {

    private val mainViewModel: MainViewModel by viewModels()

}