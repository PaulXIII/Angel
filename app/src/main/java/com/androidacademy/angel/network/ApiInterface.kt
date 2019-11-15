package com.androidacademy.angel.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiInterface {

    @Headers(
        "Content-Type: text/html; charset=utf-8",
        "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
        "Cookie: has_js=1; _ga=GA1.2.46641490.1573487740; _gid=GA1.2.1345523209.1573487740; _gat=1",
        "Host: angel-search.by"
    )
    @GET("/search")
    suspend fun getAdverts(): ResponseBody
}