package com.technipixl.prepaevalfinaland.ui.search

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.prepaevalfinaland.R
import com.technipixl.prepaevalfinaland.databinding.BysearchLayoutBinding
import com.technipixl.prepaevalfinaland.databinding.CocktailItemLayoutBinding
import com.technipixl.prepaevalfinaland.databinding.FragmentSearchBinding
import com.technipixl.prepaevalfinaland.db.database.DatabaseRepository
import com.technipixl.prepaevalfinaland.db.model.CategoryEntity
import com.technipixl.prepaevalfinaland.network.model.CocktailResponse
import com.technipixl.prepaevalfinaland.network.service.CocktailServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var adapter: SearchAdapter? = null
    private val randomCocktailService by lazy { CocktailServiceImpl() }
    var selectedSearchByAlcool: String? = null
    var categoryList: List<CategoryEntity>? = null
    var selectedCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.searchByName.setOnClickListener {
            showAlertDialogBySearch()
        }

        binding.searchByAlcool.setOnClickListener {
            showAlertDialogByAlcool()
        }

        CoroutineScope(IO).launch {
            categoryList = DatabaseRepository.getCategory(requireContext())
        }

        binding.searchByCategories.setOnClickListener {
            showAlertDialogByCategory()
        }



        return binding.root
    }


    private fun retrieveDataBySearch(search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = randomCocktailService.getCocktailList(search)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body().let { body ->
                            if (body != null) {
                                setupRecyclerView(body)
                            }
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

    private fun retrieveDataByCategory(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = randomCocktailService.searchByCategory(category)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body().let { body ->
                            if (body != null) {
                                setupRecyclerView(body)
                            }
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

    private fun showAlertDialogBySearch() {
        // Create an alert builder
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Search")

        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.bysearch_layout, null)
        builder.setView(customLayout)

        // add a button
        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
            val editText = customLayout.findViewById<EditText>(R.id.by_search_edit_text).text.toString()
             retrieveDataBySearch(editText)
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun showAlertDialogByCategory() {

        val array = categoryList?.map { it.name }?.toTypedArray()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose a category")
            .setItems(array) { dialog, which ->
                selectedCategory = array?.get(which)

                retrieveDataByCategory(selectedCategory!!)
            }
        builder.create().show()
    }

    private fun showAlertDialogByAlcool() {
        val array = listOf("Alcoholic", "Non_Alcoholic").toTypedArray()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose a type")
            .setItems(array) { dialog, which ->
                selectedSearchByAlcool = array[which]

                retrieveDataByAlcool(selectedSearchByAlcool!!)
            }
        builder.create().show()
    }

    private fun retrieveDataByAlcool(alcool: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = randomCocktailService.searchByAlcool(alcool)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body().let { body ->
                            if (body != null) {
                                setupRecyclerView(body)
                            }
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

    private fun setupRecyclerView(cocktailResponse: CocktailResponse) {
        binding.cocktailRecylerview.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false)

        adapter = SearchAdapter(cocktailResponse) { cocktail ->
            //goToDetail(crypto)
        }
        binding.cocktailRecylerview.adapter = adapter
    }
}