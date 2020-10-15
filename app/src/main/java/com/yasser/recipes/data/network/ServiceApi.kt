package com.yasser.recipes.data.network

import com.yasser.recipes.model.Recipe
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ServiceApi {

    companion object{
        operator  fun invoke(connectionStatus: ConnectionStatus):ServiceApi{


            val clientbuilder = OkHttpClient.Builder()
                .addInterceptor(connectionStatus)
                .build()

            return Retrofit.Builder()
                .client(clientbuilder)
                .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceApi::class.java)

        }
    }

    @GET("recipes.json")
    suspend fun GetRecipes(): Response<List<Recipe>>

}