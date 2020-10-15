package com.yasser.recipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//
//calories: "751 kcal",
//carbos: "105 g",
//country: "GB",
//description: "Head Chef Patrick doesn’t like fuss. He’s always telling us that the best kind of food is simple, soulful grub that makes you feel loved. That said, every dinner is a chance to practice your presentation skills. Bigger plates are a great way of framing your food and a sprinkle of herbs or a drizzle of olive oil at the end gives everything a bit more pizzazz. For this recipe, we took classic Mexican ingredients and played with the presentation to create something that’s as tasty to the eye as it is to the tongue. Arriba!",
//difficulty: 0,
//fats: "4 g",
//headline: "with Cucumber Salad",
//id: "5331430fff604d557f8b456c",
//image: "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/sausage-risotto-wk41-a201d2fa.jpg",
//name: "Sausage Risotto",
//proteins: "35 g",
//thumb: "https://img.hellofresh.com/f_auto,q_auto,w_300/hellofresh_s3/image/sausage-risotto-wk41-a201d2fa.jpg",
//time: "PT35M"
@Entity(tableName = "Recipe_data")
class Recipe(
var calories:String,
var carbos:String,
var country:String?,
var description:String,
var difficulty:Int,
var fats:String,
var headline:String,
var image:String,
var name:String,
var proteins:String,
var thumb:String,
var time:String
) {
    @PrimaryKey
    var id:String =""
}