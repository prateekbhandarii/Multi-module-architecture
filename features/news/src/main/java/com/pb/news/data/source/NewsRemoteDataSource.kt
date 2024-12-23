package com.pb.news.data.source

import com.pb.common.models.Article


interface NewsRemoteDataSource {

    suspend fun getNews(): MutableList<Article>

}