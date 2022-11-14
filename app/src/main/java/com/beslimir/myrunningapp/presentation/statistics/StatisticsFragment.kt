package com.beslimir.myrunningapp.presentation.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beslimir.myrunningapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val statisticsViewModel: StatisticsViewModel by viewModels()

}