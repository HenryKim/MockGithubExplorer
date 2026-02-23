package com.example.androidassignment.core.data.di

import com.example.androidassignment.core.data.repository.GithubRepository
import com.example.androidassignment.core.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that binds the [GithubRepositoryImpl] to the [GithubRepository] interface.
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}