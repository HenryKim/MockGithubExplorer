package com.example.androidassignment.core.domain

// Data models defined based on GitHub API requirements
data class GithubUser(
    val name: String,
    val avatarUrl: String
)

data class GithubRepo(
    val name: String,
    val description: String?,
    val forks: Int,
    val stargazersCount: Int,
    val updatedAt: String
)