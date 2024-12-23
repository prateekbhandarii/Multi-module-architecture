package com.pb.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class NewsDao {

    @Query("SELECT * FROM db_news")
    abstract fun getNewsFromDatabase(): DbNews?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNewsToDatabase(posts: DbNews)
}
