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

package com.example.composelambda.async

sealed class OnResult<out R> {
    class OnInit<T> : OnResult<T>()
    data class OnWaiting<out T>(val data: T?) : OnResult<T>()
    data class OnSuccess<out T>(val data: T) : OnResult<T>()
    data class OnError<T>(val exception: Throwable, val error: T?) : OnResult<T>()
}

val OnResult<*>.Waiting
    get() = this is OnResult.OnWaiting && data != null

val OnResult<*>.succeeded
    get() = this is OnResult.OnSuccess && data != null

val OnResult<*>.error
    get() = this is OnResult.OnError && error != null

inline fun <reified T> T.onSuccess(): OnResult<T> {
    return OnResult.OnSuccess(this)
}

fun <T : Throwable, E> T.onError(error: E? = null): OnResult<E> {
    return OnResult.OnError(this, error)
}
