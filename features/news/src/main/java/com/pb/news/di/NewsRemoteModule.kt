package com.pb.news.di

import com.pb.news.data.service.NewsService
import com.pb.news.data.source.NewsRemoteDataSource
import com.pb.news.data.source.NewsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

// This module is installed in the SingletonComponent, ensuring application-wide singletons.
@Module(includes = [NewsRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class NewsRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {

        /**
         * Binds the implementation of NewsRemoteDataSource to its interface.
         *
         * @param remoteDataSourceImpl The concrete implementation of NewsRemoteDataSource.
         * @return An instance of NewsRemoteDataSource.
         */

        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: NewsRemoteDataSourceImpl
        ): NewsRemoteDataSource
    }

    /**
     * Provides an instance of NewsService.
     *
     * @param retrofit The Retrofit instance used to create the service.
     * @return An implementation of NewsService.
     */

    @Provides
    fun provideService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)
}