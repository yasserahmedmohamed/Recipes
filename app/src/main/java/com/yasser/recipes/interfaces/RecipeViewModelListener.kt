package com.yasser.recipes.interfaces

interface RecipeViewModelListener {

    fun InternetConnection(IsConnected:Boolean)

    fun OnFilterSelected(filterItem:Int)

    fun OnLoadingData()
    fun OnLoadFinish()
}