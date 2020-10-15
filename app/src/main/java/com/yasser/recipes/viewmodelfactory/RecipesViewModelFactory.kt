package com.yasser.recipes.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.recipes.repository.RecipeRepository
import com.yasser.recipes.ui.recipe_list.RecipeViewModel


@Suppress("UNCHECKED_CAST")

class RecipesViewModelFactory (
    private val repository: RecipeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return RecipeViewModel(repository) as T
    }

}