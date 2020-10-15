package com.yasser.recipes.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.recipes.repository.RecipeRepository
import com.yasser.recipes.ui.recipe_details.RecipeDetailsViewModel
import com.yasser.recipes.ui.recipe_list.RecipeViewModel


@Suppress("UNCHECKED_CAST")

class RecipesDetailsViewModelFactory (
    private val repository: RecipeRepository,
   val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return RecipeDetailsViewModel(repository,application) as T
    }

}