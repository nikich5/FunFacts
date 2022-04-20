package ru.nikich5.funfacts.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/fun/facts")
    suspend fun getFacts(@Query("num") count: Int): Response<List<String>>
}