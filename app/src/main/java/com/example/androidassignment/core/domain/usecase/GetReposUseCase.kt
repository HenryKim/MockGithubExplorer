package com.example.androidassignment.core.domain.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.domain.GithubRepo
import javax.inject.Inject

/**
 * UseCase to fetch the list of public repositories for a specific user.
 */
class GetReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(userId: String): List<GithubRepo> {
        // Business logic: fetches and returns the repository list
        return repository.getUserRepos(userId)
    }
}