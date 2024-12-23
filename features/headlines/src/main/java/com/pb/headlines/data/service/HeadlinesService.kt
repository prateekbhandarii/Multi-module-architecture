package com.pb.headlines.data.service

import com.pb.common.di.API_KEY
import com.pb.common.models.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlinesService {

    @GET("v2/top-headlines")
    fun getHeadlines(
        @Query("sources") countryCode: String = "techcrunch",
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Response<NewsResponse>>
}