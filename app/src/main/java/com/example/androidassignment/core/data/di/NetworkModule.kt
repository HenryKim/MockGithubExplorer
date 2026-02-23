package com.example.androidassignment.core.data.di

import com.example.androidassignment.core.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides network-related dependencies for the application.
 *
 * This module is scoped to the [SingletonComponent] to ensure that network instances
 * like [Retrofit] and [Json] remain consistent throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            // Modern optimization: ignore unknown fields to prevent crashes [cite: 17]
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun provideGithubApi(json: Json): GithubApi {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            // 이 확장이 InternalSerializationApi를 건드리지 않도록 명확하게 호출합니다.
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(GithubApi::class.java)
    }
}