package com.example.androidassignment.usecase

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.domain.GithubUser
import com.example.androidassignment.core.domain.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUserUseCaseTest {

    private val repository: GithubRepository = mockk()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun shouldReturnUserDataWhenValidUserIdIsProvided() = runTest {
        val mockUser = GithubUser(name = "John Doe", avatarUrl = "https://avatar.url")
        coEvery { repository.getUser("john") } returns mockUser

        val result = useCase("john")

        assertEquals("John Doe", result.name)
        assertEquals("https://avatar.url", result.avatarUrl)
    }
}