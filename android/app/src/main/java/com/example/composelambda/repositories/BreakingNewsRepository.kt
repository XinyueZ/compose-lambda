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

import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.domains.BreakingNews.Companion.default
import com.example.composelambda.network.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.System.currentTimeMillis
import javax.inject.Inject

interface BreakingNewsRepository {
    fun echo(): BreakingNews
    fun fetchBreakingNews(): Flow<BreakingNews>
}

class BreakingNewsRepositoryImpl @Inject constructor(private val newsService: NewsService) :
    BreakingNewsRepository {
    override fun echo(): BreakingNews = default

    override fun fetchBreakingNews(): Flow<BreakingNews> {
        return flow {
            if (currentTimeMillis() % 2 == 0) {
                emit(newsService.getBreakingNews())
            } else {
                emit(BreakingNews.error)
            }
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }
}
