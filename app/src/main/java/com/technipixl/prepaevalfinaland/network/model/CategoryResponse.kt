package com.technipixl.prepaevalfinaland.network.model

data class CategoryResponse (
    val drinks: MutableList<Category>
)

data class Category(
    val strCategory: String
)
