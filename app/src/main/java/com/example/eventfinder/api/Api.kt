package com.example.eventfinder.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    val loggingInterceptor= HttpLoggingInterceptor()
    private const val baseUrl = "http://192.168.0.73:8000"
    //private const val baseUrl = "http://10.3.224.213:8000"
    private val clientBuilder = OkHttpClient.Builder()
    private fun getApi() : Retrofit
    {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = clientBuilder.addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    fun<T> buildService(service: Class<T>): T{
        return getApi().create(service)
    }
}