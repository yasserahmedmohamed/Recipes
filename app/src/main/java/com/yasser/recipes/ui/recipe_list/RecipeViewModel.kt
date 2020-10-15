package com.yasser.recipes.ui.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasser.recipes.data.network.ApiException
import com.yasser.recipes.interfaces.RecipeViewModelListener
import com.yasser.recipes.model.Recipe
import com.yasser.recipes.repository.RecipeRepository
import com.yasser.recipes.utils.Coroutines
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class RecipeViewModel(private val recipeRepo: RecipeRepository) : ViewModel() {

    var listener: RecipeViewModelListener? = null

    var DisplayRecipes = MutableLiveData<List<Recipe>>()

    fun GetRecipesData() {

        listener?.OnLoadingData()
        Coroutines.io {
            try {
                val resApi = recipeRepo.GetRecipesFromApi()
                listener?.OnLoadFinish()
                if (resApi.size != 0) {
                    listener?.InternetConnection(true)
                    recipeRepo.UpdateDbData(resApi)
                    DisplayRecipes.postValue(resApi)
                }
            } catch (e: ApiException) {
                listener?.InternetConnection(false)
                listener?.OnLoadFinish()
                Coroutines.io {
                    val res = recipeRepo.GetRecipesFrmDB()
                    DisplayRecipes.postValue(res)

                }
                print(e.message)

            }

            val FilterInt= GetFilter()
            if (FilterInt != -1)
            {
                listener?.OnFilterSelected(FilterInt)
            }


        }
    }

    fun SearchWithText(text: String) {
        Coroutines.io {
            val res = recipeRepo.GetRecipesByName(text)
            DisplayRecipes.postValue(res)
        }
    }

    fun GetRecipesFrmDB() {
        Coroutines.io {
            val res = recipeRepo.GetRecipesFrmDB()

            DisplayRecipes.postValue(res)
        }
    }

    fun FilterByCaloris() {
        Coroutines.io {
            val res = recipeRepo.GetFilterByCaloris()

            DisplayRecipes.postValue(res)
        }

    }

    fun FilterByFats() {
        Coroutines.io {
            val res = recipeRepo.GetFilterByFats()

            DisplayRecipes.postValue(res)
        }

    }

    fun SaveFilterItem(item:Int)
    {
        recipeRepo.SaveFilter(item)
    }

    fun GetFilter():Int{
        return recipeRepo.GetFilter()
    }

    fun checkIsDataAvailable() {
        if (DisplayRecipes.value != null)
        {
            if (DisplayRecipes.value!!.size==0)
            {
                GetRecipesData()
            }
        }
        else{
            GetRecipesData()
        }
    }

}