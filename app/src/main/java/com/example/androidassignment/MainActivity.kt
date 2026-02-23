package com.example.androidassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidassignment.feature.detail.RepoDetailScreen
import com.example.androidassignment.feature.search.GithubSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // Set up Navigation Graph
                NavHost(navController = navController, startDestination = "search") {
                    composable("search") {
                        GithubSearchScreen(onNavigateToDetail = { totalForks ->
                            navController.navigate("detail/$totalForks")
                        })
                    }
                    composable(
                        "detail/{totalForks}",
                        arguments = listOf(navArgument("totalForks") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val forks = backStackEntry.arguments?.getInt("totalForks") ?: 0
                        RepoDetailScreen(
                            totalForks = forks,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}