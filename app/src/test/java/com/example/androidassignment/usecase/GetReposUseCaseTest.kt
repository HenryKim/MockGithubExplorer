package com.example.androidassignment.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.usecase.GetReposUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetReposUseCaseTest {

    private val repository: GithubRepository = mockk()
    private val useCase = GetReposUseCase(repository)

    @Test
    fun shouldReturnRepoListWhenUserHasPublicRepositories() = runTest {
        val mockRepos = listOf(
            GithubRepo("Repo1", "Desc1", 10, 5, "2024-01-02"),
            GithubRepo("Repo2", "Desc2", 20, 10, "2024-01-02")
        )
        coEvery { repository.getUserRepos("john") } returns mockRepos

        val result = useCase("john")

        assertEquals(2, result.size)
        assertEquals("Repo1", result[0].name)
        assertEquals(20, result[1].stargazersCount)
    }

    @Test
    fun shouldReturnEmptyListWhenUserHasNoRepositories() = runTest {
        coEvery { repository.getUserRepos("emptyUser") } returns emptyList()

        val result = useCase("emptyUser")

        assertTrue(result.isEmpty())
    }
}