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

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.composelambda.async.OnResult
import com.example.composelambda.async.OnResult.OnError
import com.example.composelambda.async.OnResult.OnWaiting
import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.repositories.BreakingNewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class BreakingNewsViewModel @ViewModelInject constructor(
    private val repository: BreakingNewsRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val breakingNewsState: Flow<OnResult<BreakingNews>> = repository.fetchBreakingNews().onStart {
        emit(OnWaiting(null))
    }.catch {
        emit(OnError(it, BreakingNews.empty))
    }

    fun echo(): BreakingNews = repository.echo()
}
