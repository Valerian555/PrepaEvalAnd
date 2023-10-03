package com.technipixl.prepaevalfinaland.db.database

import android.content.Context
import android.util.Log
import com.technipixl.prepaevalfinaland.db.model.CategoryEntity
import com.technipixl.prepaevalfinaland.network.model.Category
import com.technipixl.prepaevalfinaland.network.service.CocktailServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DatabaseRepository {

    companion object {
        var appDataBase: AppDataBase? = null

        private val randomCocktailService by lazy { CocktailServiceImpl() }
        private var categoryList: MutableList<Category>? = null


        fun initializeDB(context: Context): AppDataBase {
            val db = AppDataBase.getDb(context)
            CoroutineScope(Dispatchers.IO).launch {
                val expenseType = db.categoryDao().getAllCategory()
                if (expenseType.isNullOrEmpty()) {
                        val response = randomCocktailService.getCategoryList()
                        withContext(Dispatchers.Main) {
                            try {
                                if (response.isSuccessful) {
                                    categoryList = response.body()?.drinks

                                    categoryList?.map { category -> category.strCategory }
                                        ?.forEach { name ->
                                            db.categoryDao().insert(CategoryEntity(name = name))
                                        }
                                }
                            } catch (e: HttpException) {
                                print(e)
                            } catch (e: Throwable) {
                                print(e)
                            }

                    }
                }
            }

            return db
        }

        fun getCategory(context: Context): List<CategoryEntity> {
            if (appDataBase == null) {
                appDataBase = initializeDB(context)
            }
            return appDataBase!!.categoryDao().getAllCategory()
        }
    }
}