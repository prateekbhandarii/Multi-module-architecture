package com.pb.news.data.source

import com.pb.common.models.Article


interface NewsLocalDataSource {
    suspend fun getNews(): List<Article>?
    suspend fun saveNews(posts: List<Article>)
}