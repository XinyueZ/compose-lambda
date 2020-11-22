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

package com.example.composelambda.domains

data class BreakingNews(
    val title: String,
    val description: String,
    val image: String
) {
    companion object {
        @JvmStatic
        val EchoBreakingNews = BreakingNews(
            "Breaking News",
            "Hi, this is the breaking news",
            "https://dl.dropboxusercontent.com/s/4o1nq8pdtuv5vf6/Screenshot%202020-11-22%20at%2015.43.54.png"
        )
    }
}
