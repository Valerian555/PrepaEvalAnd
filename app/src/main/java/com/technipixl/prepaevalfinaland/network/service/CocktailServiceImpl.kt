package com.technipixl.prepaevalfinaland.network.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.technipixl.prepaevalfinaland.network.model.CocktailResponse
import retrofit2.Response
import retrofit2.create

class CocktailServiceImpl: BaseServiceImpl() {

    suspend fun getRandomCocktail(): Response<CocktailResponse> =
        getRetrofit().create(CocktailService::class.java).getRandomCocktail()

    suspend fun getCocktailList(cocktailName: String): Response<CocktailResponse> =
        getRetrofit().create(CocktailService::class.java).getCocktailList(cocktailName)

    suspend fun searchByAlcool(alcool: String): Response<CocktailResponse> =
        getRetrofit().create(CocktailService::class.java).searchByAlcool(alcool)
}