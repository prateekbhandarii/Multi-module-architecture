package com.pb.headlines.data.source

import com.pb.common.models.Article

interface HeadlinesRemoteDataSource {

    suspend fun getHeadlines(): MutableList<Article>

}