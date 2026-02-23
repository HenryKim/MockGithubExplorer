package com.example.androidassignment.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.core.domain.usecase.GetTotalForksUseCase
import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.GithubUser
import com.example.androidassignment.core.domain.usecase.GetReposUseCase
import com.example.androidassignment.core.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of the GitHub user search feature.
 *
 * This class orchestrates the fetching of user profiles, repository lists, and total fork counts
 * by interacting with the domain layer's UseCases. It exposes a reactive [GithubUiState]
 * through a [StateFlow] to be consumed by the UI.
 *
 * @property getUserUseCase UseCase to retrieve basic GitHub user profile information.
 * @property getReposUseCase UseCase to retrieve the list of repositories for a specific user.
 * @property getTotalForksUseCase UseCase to calculate the aggregate number of forks across all repositories.
 */
@HiltViewModel
class GithubViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getReposUseCase: GetReposUseCase,
    private val getTotalForksUseCase: GetTotalForksUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(GithubUiState())
    val uiState: StateFlow<GithubUiState> = _uiState.asStateFlow()

    /**
     * Searches for a GitHub user and their repositories.
     * Includes error handling and loading state management.
     */
    fun searchUser(userId: String) {
        if (userId.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Concurrent data fetching for better performance
                val userDeferred = async { getUserUseCase(userId) }
                val reposDeferred = async { getReposUseCase(userId) }
                val totalForksDeferred = async { getTotalForksUseCase(userId) }

                val user = userDeferred.await()
                val repos = reposDeferred.await()
                val totalForks = totalForksDeferred.await()

                _uiState.update {
                    it.copy(
                        user = user,
                        repos = repos,
                        totalForks = totalForks,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Centralized error handling for UI feedback
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}

/**
 * UI State representing the screen at any given time.
 * Optimized for Compose recomposition.
 */
data class GithubUiState(
    val user: GithubUser? = null,
    val repos: List<GithubRepo> = emptyList(),
    val totalForks: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)