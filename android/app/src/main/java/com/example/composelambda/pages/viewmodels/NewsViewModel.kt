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

package com.example.composelambda.pages.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composelambda.async.OnResult
import com.example.composelambda.async.OnResult.OnError
import com.example.composelambda.async.OnResult.OnNothing
import com.example.composelambda.async.OnResult.OnWaiting
import com.example.composelambda.async.onSuccess
import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.domains.PremiumNews
import com.example.composelambda.repositories.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository,
) : ViewModel() {

    var breakingNewsDetail: OnResult<BreakingNews> by mutableStateOf(OnNothing()) //= repository.breakingNewsStorage
        private set

    var premiumNewsDetail: OnResult<PremiumNews> by mutableStateOf(OnNothing()) //= repository.premiumNewsStorage
        private set

    var premiumNewsState: OnResult<PremiumNews> by mutableStateOf(OnNothing())
        private set

    var breakingNewsState: OnResult<BreakingNews> by mutableStateOf(OnNothing())
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchPremiumNews() = viewModelScope.launch {

        repository.fetchPremiumNews().onStart {
            emit(OnWaiting(null))
        }.catch {
            emit(OnError(it, PremiumNews.empty))
        }.collect {
            premiumNewsState = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchBreakingNews() = viewModelScope.launch {

        repository.fetchBreakingNews().onStart {
            emit(OnWaiting(null))
        }.catch {
            emit(OnError(it, BreakingNews.empty))
        }.collect {
            breakingNewsState = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchBreakingNewsDetail() = viewModelScope.launch {

        repository.breakingNewsStorage.collectLatest {
            breakingNewsDetail = it.onSuccess()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchPremiumNewsNewsDetail() = viewModelScope.launch {

        repository.premiumNewsStorage.collectLatest {
            premiumNewsDetail = it.onSuccess()
        }
    }
}
