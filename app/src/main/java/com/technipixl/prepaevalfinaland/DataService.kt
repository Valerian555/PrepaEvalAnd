package com.technipixl.prepaevalfinaland

import android.util.Log
import com.technipixl.prepaevalfinaland.network.service.CocktailServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException

object DataService {
    private val randomCocktailService by lazy { CocktailServiceImpl() }
    fun retrieveCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = randomCocktailService.getCategoryList()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }
}