package com.yasser.recipes.interfaces

import com.yasser.recipes.model.Recipe

interface RecipeItemListener {
    fun OnItemTapped(data:Recipe)
}