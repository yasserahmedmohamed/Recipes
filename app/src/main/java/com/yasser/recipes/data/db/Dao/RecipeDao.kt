package com.yasser.recipes.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yasser.recipes.model.Recipe
import org.jetbrains.annotations.NotNull

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe:Recipe)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(objects: List<Recipe>)


    @Query("select * from recipe_data")
     fun GetAllRecipe(): List<Recipe>

    @Query("select * from recipe_data where name LIKE :str")
     fun SearchByName(str:String): List<Recipe>

    @Query("select * from recipe_data ORDER BY fats ")
     fun OrderByFat(): List<Recipe>

    @Query("select * from recipe_data ORDER BY calories ")
     fun OrderByCaloris(): List<Recipe>

    @Query("delete  from recipe_data")
   suspend fun DeleteAllData()

    @Query("select * from recipe_data where id = :reID")
    fun GetByID(reID:String):Recipe
}