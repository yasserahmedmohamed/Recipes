package com.yasser.recipes.repository

import androidx.lifecycle.LiveData
import com.yasser.recipes.data.db.AppDatabase
import com.yasser.recipes.data.network.SafeApiRequest
import com.yasser.recipes.data.network.ServiceApi
import com.yasser.recipes.data.preferences.PreferenceProvider
import com.yasser.recipes.model.Recipe

class RecipeRepository(private val serviceApi: ServiceApi,
                       private val db: AppDatabase,
                       private val mShared: PreferenceProvider
) : SafeApiRequest() {

    suspend fun GetRecipesFromApi():List<Recipe>{

        val response = apiRequest { serviceApi.GetRecipes() }

        return response
    }


    suspend fun UpdateDbData(recipe: List<Recipe>){

        db.getRecipeDao().DeleteAllData()
        db.getRecipeDao().insertAll(recipe)

    }


    suspend fun GetRecipesFrmDB():List<Recipe>{

        return db.getRecipeDao().GetAllRecipe()
    }

    suspend fun GetRecipesByName(str:String):List<Recipe>{

        return db.getRecipeDao().SearchByName("%${str}%")
    }

    fun GetRecipeByID(id:String):Recipe{

        return db.getRecipeDao().GetByID(id)
    }

    fun GetFilterByCaloris():List<Recipe>{

        return db.getRecipeDao().OrderByCaloris()
    }

    fun GetFilterByFats():List<Recipe>{

        return db.getRecipeDao().OrderByFat()
    }

    fun SaveFilter(item:Int){
        mShared.SaveFilterType(item)
    }

    fun GetFilter():Int{
       return mShared.GetFilterType()
    }

}