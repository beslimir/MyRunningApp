package com.beslimir.myrunningapp.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.beslimir.myrunningapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {



}