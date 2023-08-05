package com.example.eventfinder.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Api {
    val loggingInterceptor= HttpLoggingInterceptor()
    //private const val baseUrl = "http://192.168.0.73:8000" // Home
    //private const val baseUrl = "http://10.3.224.213:8000" // School NCI
    private const val baseUrl = "http://104.131.12.252:9090/" // Erkan Demir Server
    private val clientBuilder = OkHttpClient.Builder()
    private fun getApi() : Retrofit
    {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = clientBuilder.addInterceptor(loggingInterceptor).build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
    fun<T> buildService(service: Class<T>): T{
        return getApi().create(service)
    }
}