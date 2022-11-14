package com.beslimir.myrunningapp.presentation.statistics

import androidx.lifecycle.ViewModel
import com.beslimir.myrunningapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

}