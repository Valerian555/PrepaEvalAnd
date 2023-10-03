package com.technipixl.prepaevalfinaland.network.model

data class CocktailResponse(
    val drinks: MutableList<Cocktail>
)

data class Cocktail(
    val idDrink : Long?,
    val strDrink: String,
    val strCategory: String,
    val strDrinkThumb: String
)
