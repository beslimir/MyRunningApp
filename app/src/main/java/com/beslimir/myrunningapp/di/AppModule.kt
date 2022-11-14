package com.beslimir.myrunningapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.beslimir.myrunningapp.data.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}