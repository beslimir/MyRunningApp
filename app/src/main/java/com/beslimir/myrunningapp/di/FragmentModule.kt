package com.beslimir.myrunningapp.di

import android.content.SharedPreferences
import com.beslimir.myrunningapp.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun provideFirstTimeToggle(sharedPrefs: SharedPreferences) =
        sharedPrefs.getBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)

}