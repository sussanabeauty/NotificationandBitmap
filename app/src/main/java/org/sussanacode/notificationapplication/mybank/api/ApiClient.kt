package org.sussanacode.notificationapplication.mybank.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val myRetrofit: Retrofit by lazy{
        val loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://psmobitech.com/bankapi/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        retrofit
    }

    val apiService: ApiService by lazy{
        myRetrofit.create(ApiService::class.java)
    }
}