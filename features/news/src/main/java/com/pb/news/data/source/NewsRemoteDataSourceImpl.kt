package com.pb.news.data.source

import com.pb.common.di.qualifiers.IO
import com.pb.common.exception.NoDataException
import com.pb.news.data.service.NewsService
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsRemoteDataSourceImpl @Inject constructor(
    private val service: NewsService,
    @IO private val context: CoroutineContext
) : NewsRemoteDataSource {
    override suspend fun getNews() = withContext(context) {
        try {
            val response = service.getNews().await()
            if (response.isSuccessful) {
                response.body()?.articles ?: throw NoDataException("Response body is null")
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw IOException("Network error occurred: ${e.message}", e)
        }
    }
}
