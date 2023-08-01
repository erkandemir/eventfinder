package com.example.eventfinder.api

interface IApiListener<T> {
    fun onComplate(response : T)
    fun onError()
}