package com.example.androidassignment.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.usecase.GetTotalForksUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTotalForksUseCaseTest {

    private val repository: GithubRepository = mockk()
    private val useCase = GetTotalForksUseCase(repository)

    @Test
    fun shouldCalculateCorrectTotalForksWhenMultipleReposExist() = runTest {
        val mockRepos = listOf(
            GithubRepo(
                name = "Repo1",
                forks = 1000,
                stargazersCount = 500,
                description = "",
                updatedAt = ""
            ),
            GithubRepo(name = "Repo2", forks = 2500, stargazersCount = 300, description = "", updatedAt = ""),
            GithubRepo(name = "Repo3", forks = 2000, stargazersCount = 100, description = "", updatedAt = "")
        )
        coEvery { repository.getUserRepos(any()) } returns mockRepos

        val result = useCase("testUser")

        assertEquals(5500, result)
    }

    @Test
    fun shouldReturnZeroWhenNoReposFound() = runTest {
        coEvery { repository.getUserRepos(any()) } returns emptyList()

        val result = useCase("unknownUser")

        assertEquals(0, result)
    }
}