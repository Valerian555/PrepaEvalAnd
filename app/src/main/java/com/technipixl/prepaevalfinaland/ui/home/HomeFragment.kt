package com.technipixl.prepaevalfinaland.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.technipixl.prepaevalfinaland.databinding.FragmentHomeBinding
import com.technipixl.prepaevalfinaland.network.model.Cocktail
import com.technipixl.prepaevalfinaland.network.service.CocktailServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val randomCocktailService by lazy { CocktailServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        retrieveData()

        binding.randomCocktailButton.setOnClickListener {
            retrieveData()
        }

        return binding.root
    }

    private fun retrieveData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = randomCocktailService.getRandomCocktail()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val result = response.body()?.drinks?.get(0)
                        if (result != null) {
                            setupUI(result)
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

    private fun setupUI(cocktail: Cocktail) {
        setupImage(cocktail.strDrinkThumb, binding.cocktailImage)
        binding.cocktailName.text = cocktail.strDrink
        binding.cocktailCategory.text = cocktail.strCategory
    }

    private fun setupImage(url: String, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .fit()  //redimensione l'image par rapport à son conteneur
            .centerCrop()  //option permettant de choisir le type d'affichage de l'image (il y a différents types)
            .into(imageView)
    }

}