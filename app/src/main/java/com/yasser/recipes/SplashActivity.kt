package com.yasser.recipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yasser.recipes.ui.recipe_list.RecipesActivity
import com.yasser.recipes.utils.Coroutines
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Coroutines.main {
            OpenMainPage()
        }

    }


   suspend fun OpenMainPage(){
        delay(2000)
       val intent = Intent(this,RecipesActivity::class.java).also {
           it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
           it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
           it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
           startActivity(it)
       }
    }
}
