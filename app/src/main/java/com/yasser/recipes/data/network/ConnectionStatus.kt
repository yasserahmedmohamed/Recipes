package com.yasser.recipes.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionStatus ( context: Context) : Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!IsInternetAvailable())
            throw ApiException("Make sure you have an active data connection")

        try {
            return chain.proceed(chain.request())
        }catch (e: IOException)
        {
            throw ApiException("Make sure you have an active data connection")

        }

    }

    private fun IsInternetAvailable(): Boolean {
        val connectivitymanager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivitymanager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}