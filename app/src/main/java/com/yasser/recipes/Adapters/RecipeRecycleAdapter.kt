package com.yasser.recipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yasser.recipes.R
import com.yasser.recipes.interfaces.RecipeItemListener
import com.yasser.recipes.model.Recipe

class RecipeRecycleAdapter(val context: Context,val recipesList: ArrayList<Recipe>) :
    RecyclerView.Adapter<RecipeRecycleAdapter.RecipeViewHolder>() {

    var listener: RecipeItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recipe,
            parent, false
        )
        val vHolder = RecipeViewHolder(view)
        return vHolder
    }

    fun UpdateList(items:ArrayList<Recipe>){
        recipesList.clear()
        recipesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun getItem(position: Int): Recipe {
        return recipesList.get(position)
    }

    fun clearData(){
        recipesList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener?.OnItemTapped(getItem(position))
        }
        holder.OnBind(context,getItem(position))
    }


    class RecipeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var recipe_image: ImageView
        lateinit var recipe_name: TextView
        lateinit var recipe_headline: TextView
        lateinit var recipe_Caloris: TextView

        lateinit var recipe_Fat: TextView

        init {
            recipe_image = itemView.findViewById(R.id.recipe_Image)
            recipe_name = itemView.findViewById(R.id.recipe_name)
            recipe_headline = itemView.findViewById(R.id.recipe_headline)
            recipe_Caloris = itemView.findViewById(R.id.recipe_Caloris)
            recipe_Fat = itemView.findViewById(R.id.recipe_Fat)


        }

        fun OnBind(context:Context,mRecipe: Recipe) {

            Picasso.with(context)
                .load(mRecipe.thumb)
                .error(R.drawable.error_loading_image)
                .into(recipe_image)

            recipe_name.text = mRecipe.name
            recipe_headline.text = mRecipe.headline
            recipe_Caloris.text = mRecipe.calories
            recipe_Fat.text=mRecipe.fats

        }
    }
}