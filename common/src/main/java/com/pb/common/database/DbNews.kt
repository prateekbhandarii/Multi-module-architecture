package com.pb.common.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_news")
data class DbNews(
    @PrimaryKey val id: Int,
    val posts: String
)