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

package com.example.composelambda.repositories

import com.example.composelambda.async.OnResult
import com.example.composelambda.async.onError
import com.example.composelambda.async.onSuccess
import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.domains.PremiumNews
import com.example.composelambda.network.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.System.currentTimeMillis
import javax.inject.Inject

interface NewsRepository {
    fun fetchPremiumNews(): Flow<OnResult<PremiumNews>>
    fun fetchBreakingNews(): Flow<OnResult<BreakingNews>>
}

class NewsRepositoryImpl @Inject constructor(private val newsService: NewsService) :
    NewsRepository {

    override fun fetchPremiumNews(): Flow<OnResult<PremiumNews>> {
        return flow {
            when {
                currentTimeMillis() % 3 == 0 -> {
                    // Some logic errors to cause exception
                    emit(IllegalArgumentException("Cannot fetch data.").onError(PremiumNews.empty))
                }
                currentTimeMillis() % 5 == 0 -> {
                    // Some internal fatal errors
                    throw IllegalArgumentException("Fatal issue!")
                }
                else -> {
                    // Give the response wrapper out
                    emit(newsService.getPremiumNews().onSuccess())
                }
            }
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    override fun fetchBreakingNews(): Flow<OnResult<BreakingNews>> {
        return flow {
            when {
                currentTimeMillis() % 3 == 0 -> {
                    // Some logic errors to cause exception
                    emit(IllegalArgumentException("Cannot fetch data.").onError(BreakingNews.empty))
                }
                currentTimeMillis() % 5 == 0 -> {
                    // Some internal fatal errors
                    throw IllegalArgumentException("Fatal issue!")
                }
                else -> {
                    // Give the response wrapper out
                    emit(newsService.getBreakingNews().onSuccess())
                }
            }
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }
}
