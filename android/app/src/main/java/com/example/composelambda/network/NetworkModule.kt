/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.composelambda.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(): Retrofit {
        // val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            // .addConverterFactory(Json.asConverterFactory(contentType))
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
