package com.pb.headlines.di

import com.pb.headlines.data.service.HeadlinesService
import com.pb.headlines.data.source.HeadlinesRemoteDataSource
import com.pb.headlines.data.source.HeadlinesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

// This module is installed in the SingletonComponent, ensuring application-wide singletons.
@Module(includes = [HeadlinesRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class HeadlinesRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {

        /**
         * Binds the implementation of HeadlinesRemoteDataSource to its interface.
         *
         * @param remoteDataSourceImpl The concrete implementation of HeadlinesRemoteDataSource.
         * @return An instance of HeadlinesRemoteDataSource.
         */

        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: HeadlinesRemoteDataSourceImpl
        ): HeadlinesRemoteDataSource
    }

    /**
     * Provides an instance of HeadlinesService.
     *
     * @param retrofit The Retrofit instance used to create the service.
     * @return An implementation of HeadlinesService.
     */

    @Provides
    fun provideService(retrofit: Retrofit): HeadlinesService =
        retrofit.create(HeadlinesService::class.java)
}