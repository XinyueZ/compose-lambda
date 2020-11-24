package com.example.composelambda.network

import com.example.composelambda.domains.BreakingNews
import retrofit2.Call
import retrofit2.http.GET


interface  NewsService {
    @GET("/s/7fv2e7hsyz21rza/breaking-news.json")
    fun getBreakingNews(): Call<BreakingNews>
}