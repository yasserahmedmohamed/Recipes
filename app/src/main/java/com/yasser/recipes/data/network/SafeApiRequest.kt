package com.yasser.recipes.data.network

import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {
    suspend fun <T:Any> apiRequest(call:suspend () -> Response<T>):T{
        val response = call.invoke()
        if (response.isSuccessful)
        {
            return response.body()!!

        }
        else{


            val error = response.raw().message()

//            val message = StringBuilder()

//            error?.let {
//                try {
//                    message.append(JSONObject(it).getString("title"))
//                }catch (e:JSONException){}
//            }

            // message.append("error code : ${response.code()}")

            throw ApiException(error)
        }

    }
}

class ApiException(messge:String): IOException(messge)
