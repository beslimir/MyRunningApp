package com.beslimir.myrunningapp.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.beslimir.myrunningapp.data.db.RunningDatabase
import com.beslimir.myrunningapp.other.Constants.KEY_NAME
import com.beslimir.myrunningapp.other.Constants.KEY_WEIGHT
import com.beslimir.myrunningapp.other.Constants.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(app: Application): RunningDatabase {
        return Room.databaseBuilder(
            app,
            RunningDatabase::class.java,
            "running_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPrefs: SharedPreferences) =
        sharedPrefs.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPrefs: SharedPreferences) =
        sharedPrefs.getFloat(KEY_WEIGHT, 80f)

}