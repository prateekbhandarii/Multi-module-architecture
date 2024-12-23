package com.pb.common.di

import android.app.Application
import com.pb.common.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module()
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = NewsDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesDao(
        data: NewsDatabase
    ) = data.getNewsDao()
}