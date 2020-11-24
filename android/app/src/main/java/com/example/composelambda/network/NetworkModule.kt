package com.example.composelambda.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(): Retrofit {
        //val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }


    @Provides
    fun provideService(
        retrofit: Retrofit
    ): NewsService {
        return retrofit
            .create(NewsService::class.java)
    }
}