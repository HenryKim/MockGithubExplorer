package com.example.androidassignment.core.domain.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.domain.GithubUser
import javax.inject.Inject

/**
 * UseCase to fetch basic profile information of a GitHub user.
 */
class GetUserUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(userId: String): GithubUser {
        // Business logic: delegates the call to the repository
        return repository.getUser(userId)
    }
}