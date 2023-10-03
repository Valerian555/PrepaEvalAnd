package com.technipixl.prepaevalfinaland.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.prepaevalfinaland.databinding.CocktailItemLayoutBinding
import com.technipixl.prepaevalfinaland.network.model.Cocktail
import com.technipixl.prepaevalfinaland.network.model.CocktailResponse

class SearchAdapter(
    private val cocktailResponse: CocktailResponse,
    private val onItemClick: (Cocktail) -> Unit
): RecyclerView.Adapter<SearchAdapter.CocktailViewHolder>() {

    class CocktailViewHolder(
        private val binding: CocktailItemLayoutBinding,


        private val onItemClick: (Cocktail) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(cocktail: Cocktail) {

            binding.cocktailName.text = cocktail.strDrink

            binding.container.setOnClickListener {
                onItemClick(cocktail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(
            CocktailItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return cocktailResponse.drinks.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.setup(cocktailResponse.drinks[position])
    }
}