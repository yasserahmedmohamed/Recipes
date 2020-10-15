package com.yasser.recipes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yasser.recipes.data.db.Dao.RecipeDao
import com.yasser.recipes.model.Recipe


@Database(entities = [Recipe::class],version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao




    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java
                , "MyDatabase.db"
            )
                .build()

    }


}