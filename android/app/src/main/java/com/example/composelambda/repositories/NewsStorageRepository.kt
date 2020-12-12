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

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import com.example.composelambda.News
import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.domains.PremiumNews
import com.google.protobuf.InvalidProtocolBufferException
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

interface NewsStorageRepository {
    suspend fun saveBreakingNews(breakingNews: BreakingNews)
    val breakingNewsStorage: Flow<BreakingNews>

    suspend fun savePremiumNews(premiumNews: PremiumNews)
    val premiumNewsStorage: Flow<PremiumNews>
}

class NewsStorageRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : NewsStorageRepository {

    private val dataStore: DataStore<News> =
        context.createDataStore(
            fileName = "news_storage.pb",
            serializer = NewsStorageSerializer
        )

    override suspend fun saveBreakingNews(breakingNews: BreakingNews) {
        dataStore.updateData {
            it.toBuilder()
                .setType(News.NewsType.BREAKING_NEWS)
                .setTitle(breakingNews.title)
                .setDescription(breakingNews.description)
                .setImage(breakingNews.image)
                .build()
        }
    }

    override val breakingNewsStorage: Flow<BreakingNews> = dataStore.data.filter {
        it.type == News.NewsType.BREAKING_NEWS
    }.map {
        BreakingNews(
            it.title,
            it.description,
            it.image
        )
    }

    override suspend fun savePremiumNews(premiumNews: PremiumNews) {
        dataStore.updateData {
            it.toBuilder()
                .setType(News.NewsType.PREMIUM_NEWS)
                .setTitle(premiumNews.title)
                .setDescription(premiumNews.description)
                .setImage(premiumNews.image)
                .build()
        }
    }

    override val premiumNewsStorage: Flow<PremiumNews> = dataStore.data.filter {
        it.type == News.NewsType.PREMIUM_NEWS
    }.map {
        PremiumNews(
            it.title,
            it.description,
            it.image
        )
    }
}

object NewsStorageSerializer : Serializer<News> {
    override fun readFrom(input: InputStream): News {
        try {
            return News.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: News, output: OutputStream) = t.writeTo(output)

    override val defaultValue: News
        get() = News.getDefaultInstance()
}
