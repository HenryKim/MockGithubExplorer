package com.example.androidassignment.core.domain.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import javax.inject.Inject

/**
 * Use case responsible for calculating the total number of forks across all public
 * repositories for a specified GitHub user.
 *
 * This class encapsulates the business logic for aggregating fork counts retrieved
 * from the [GithubRepository].
 */
class GetTotalForksUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    /**
     * Aggregates the fork counts from the user's public repositories.
     */
    suspend operator fun invoke(username: String): Int {
        val repos = repository.getUserRepos(username)
        return repos.sumOf { it.forks }
    }
}