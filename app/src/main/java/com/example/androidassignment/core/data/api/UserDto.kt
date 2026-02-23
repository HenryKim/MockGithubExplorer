package com.example.androidassignment.core.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("name")
    val name: String?,
    @SerialName("avatar_url")
    val avatarUrl: String
)

@Serializable
data class RepoDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("forks")
    val forks: Int
)