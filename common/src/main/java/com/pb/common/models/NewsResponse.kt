package com.pb.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)

@Parcelize
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable

@Parcelize
data class Source(
    val id: String,
    val name: String
) : Parcelable