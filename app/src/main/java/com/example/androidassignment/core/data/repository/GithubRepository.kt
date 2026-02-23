package com.example.androidassignment.core.data.repository

import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.GithubUser

interface GithubRepository {
    suspend fun getUser(userId: String): GithubUser
    suspend fun getUserRepos(userId: String): List<GithubRepo>
}