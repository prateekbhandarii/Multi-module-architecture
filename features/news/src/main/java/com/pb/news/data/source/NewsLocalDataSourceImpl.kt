package com.pb.news.data.source

import com.pb.common.database.NewsDao
import com.pb.common.di.qualifiers.IO
import com.pb.common.models.Article
import com.pb.news.database.NewsMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsLocalDataSourceImpl @Inject constructor(
    private val dao: NewsDao,
    private val newsMapper: NewsMapper,
    @IO private val context: CoroutineContext
) : NewsLocalDataSource {

    override suspend fun getNews() = withContext(context) {
        val entity = dao.getNewsFromDatabase()
        if (entity != null)
            newsMapper.to(entity)
        else
            null
    }

    override suspend fun saveNews(posts: List<Article>) {
        withContext(context) {
            val news = newsMapper.from(posts)
            dao.saveNewsToDatabase(news)
        }
    }
}