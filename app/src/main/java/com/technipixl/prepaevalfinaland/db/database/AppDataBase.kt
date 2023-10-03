package com.technipixl.prepaevalfinaland.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technipixl.prepaevalfinaland.db.dao.CategoryDao
import com.technipixl.prepaevalfinaland.db.model.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var sharedInstance: AppDataBase? = null

        fun getDb(context: Context) : AppDataBase {
            if (sharedInstance != null) return sharedInstance!!
            synchronized(this) {
                sharedInstance = Room
                    .databaseBuilder(context, AppDataBase::class.java, "cocktail.db")
                    .fallbackToDestructiveMigration()
                    .build()
                return sharedInstance!!
            }
        }
    }

}