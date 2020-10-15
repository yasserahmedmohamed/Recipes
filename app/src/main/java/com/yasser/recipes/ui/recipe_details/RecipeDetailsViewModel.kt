package com.yasser.recipes.ui.recipe_details

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.yasser.recipes.R
import com.yasser.recipes.model.Recipe
import com.yasser.recipes.repository.RecipeRepository
import com.yasser.recipes.utils.Coroutines


class RecipeDetailsViewModel(private val recipeRepo: RecipeRepository, application: Application) :
    AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    var recipeMutableLiveData = MutableLiveData<Recipe>()

    var calorisMLD = MutableLiveData<String>()
    var carbosMLD = MutableLiveData<String>()
    var fatsMLD = MutableLiveData<String>()
    var proteinsMLD = MutableLiveData<String>()

    var difficultyAndTimeMLD = MutableLiveData<String>()


      var MealImage: ObservableField<Drawable>
    lateinit var bindableFieldTarget: BindableFieldTarget

    init {
        MealImage =  ObservableField<Drawable>()

    }



    fun GetRecipeByID(ID: String) {


        context.getString(R.string.calories)
        Coroutines.io {
            val res = recipeRepo.GetRecipeByID(ID)
            if (res != null) {

               SetImage(res.image)

                recipeMutableLiveData.postValue(res)

                if (!res.carbos.isNullOrEmpty()) {
                    carbosMLD.postValue("${context.getString(R.string.details_carbos)} ${res.carbos}")
                }else{
                    carbosMLD.postValue("${context.getString(R.string.details_carbos)} --")
                }

                if (!res.calories.isNullOrEmpty()) {
                    calorisMLD.postValue("${context.getString(R.string.details_calories)} ${res.calories}")
                }else{
                    calorisMLD.postValue("${context.getString(R.string.details_calories)} --")
                }


                if (!res.fats.isNullOrEmpty()) {
                    fatsMLD.postValue("${context.getString(R.string.details_fats)} ${res.fats}")
                }else{
                    fatsMLD.postValue("${context.getString(R.string.details_fats)} --")
                }


                if (!res.proteins.isNullOrEmpty()) {
                    proteinsMLD.postValue("${context.getString(R.string.details_proteins)} ${res.proteins}")
                }else{
                    proteinsMLD.postValue("${context.getString(R.string.details_proteins)} --")
                }


                var defAndTimeStr = "${context.getString(R.string.it_is)} "

                when(res.difficulty){

                    in 0..1 ->{
                        defAndTimeStr += "${context.getString(R.string.easy)} "
                    }
                    in 2..3 ->{
                        defAndTimeStr += "${context.getString(R.string.mediam)} "
                    }

                    else ->{
                        defAndTimeStr += "${context.getString(R.string.hard)} "
                    }
                }

                if (!res.time.isNullOrEmpty())
                {
                    val time = res.time.replace("PT","").replace("M","")
                    defAndTimeStr += "${context.getString(R.string.and_take)} ${time} ${context.getString(R.string.minute)} ${context.getString(R.string.to_make)}"
                }

                difficultyAndTimeMLD.postValue(defAndTimeStr)
            }

        }

    }

    fun SetImage(url:String){
        Coroutines.main {
            bindableFieldTarget = BindableFieldTarget(MealImage, context.resources)
            Picasso.with(context)
                .load(url)
                //.placeholder(R.drawable.placeholder)
                .into(bindableFieldTarget)
        }
    }


     class BindableFieldTarget(var observableField: ObservableField<Drawable>,var resources: Resources):Target{


        //private  = null
       // private  = null

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            observableField.set(placeHolderDrawable);
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            observableField.set(errorDrawable)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            observableField.set( BitmapDrawable(resources, bitmap))
        }


    }



}