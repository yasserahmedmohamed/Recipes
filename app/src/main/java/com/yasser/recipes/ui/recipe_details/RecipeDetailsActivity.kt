package com.yasser.recipes.ui.recipe_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.yasser.recipes.R
import com.yasser.recipes.databinding.ActivityRecipeDetailsBinding
import com.yasser.recipes.viewmodelfactory.RecipesDetailsViewModelFactory
import com.yasser.recipes.viewmodelfactory.RecipesViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RecipeDetailsActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: RecipesDetailsViewModelFactory by instance()
    lateinit var viewModel: RecipeDetailsViewModel

    companion object{
        val RecipeIDINTENT = "recipe_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_recipe_details)
        val binding:ActivityRecipeDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_recipe_details)
        viewModel = ViewModelProviders.of(this,factory).get(RecipeDetailsViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        val recipeID = intent.getStringExtra(RecipeIDINTENT)

        viewModel.GetRecipeByID(recipeID!!)

    }
}
