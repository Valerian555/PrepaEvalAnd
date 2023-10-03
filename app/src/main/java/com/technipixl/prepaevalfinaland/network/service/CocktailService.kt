package com.technipixl.prepaevalfinaland.network.service

import com.technipixl.prepaevalfinaland.network.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("random.php")
    suspend fun getRandomCocktail(): Response<CocktailResponse>

    @GET("search.php")
    suspend fun getCocktailList(
        @Query("s") cocktailName: String): Response<CocktailResponse>

    @GET("filter.php")
    suspend fun searchByAlcool(
        @Query("a") alcool: String): Response<CocktailResponse>
}