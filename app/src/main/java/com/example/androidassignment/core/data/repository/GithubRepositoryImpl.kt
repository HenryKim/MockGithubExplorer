package com.example.androidassignment.core.data.repository

import com.example.androidassignment.core.data.api.GithubApi
import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.GithubUser
import javax.inject.Inject
import kotlin.collections.map

/**
 * Implementation of [GithubRepository] that fetches GitHub data from the [GithubApi].
 *
 * This class handles the retrieval of user profiles and repository lists,
 * transforming the API data transfer objects (DTOs) into domain models.
 *
 * @property api The [GithubApi] service used to perform network requests.
 */
class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : GithubRepository {

    override suspend fun getUser(userId: String): GithubUser {
        val response = api.getUser(userId)
        // Mapping Data Transfer Object (DTO) to Domain Model
        return GithubUser(
            name = response.name ?: "No Name",
            avatarUrl = response.avatarUrl
        )
    }

    override suspend fun getUserRepos(userId: String): List<GithubRepo> {
        return api.getRepos(userId).map { dto ->
            GithubRepo(
                name = dto.name,
                description = dto.description,
                forks = dto.forks,
                stargazersCount = dto.stargazersCount,
                updatedAt = dto.updatedAt
            )
        }
    }
}