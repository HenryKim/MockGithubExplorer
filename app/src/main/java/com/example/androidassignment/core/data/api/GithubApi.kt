package com.example.androidassignment.core.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserDto

    @GET("users/{userId}/repos")
    suspend fun getRepos(@Path("userId") userId: String): List<RepoDto>
}