package com.technipixl.prepaevalfinaland.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.technipixl.prepaevalfinaland.db.model.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: CategoryEntity): Long

    @Query("SELECT * FROM categoryentity")
    fun getAllCategory(): List<CategoryEntity>
}