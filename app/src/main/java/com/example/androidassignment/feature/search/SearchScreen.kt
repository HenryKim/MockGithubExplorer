package com.example.androidassignment.feature.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.androidassignment.core.domain.GithubRepo
import com.example.androidassignment.core.domain.GithubUser
import com.example.androidassignment.feature.GithubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubSearchScreen(
    viewModel: GithubViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit // Callback for navigation
) {
    val uiState by viewModel.uiState.collectAsState()
    var userId by remember { mutableStateOf("") }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("GitHub Explorer") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("Enter GitHub ID") },
                modifier = Modifier.fillMaxWidth(),
                // Keyboard action settings
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.searchUser(userId)
                    softwareKeyboardController?.hide() // Hide keyboard on search
                }),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // User Info Section (Clicking this or list can lead to detail)
            uiState.user?.let {
                UserHeader(user = it, totalForks = uiState.totalForks)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.repos) { repo ->
                    RepoItem(
                        repo = repo,
                        // Item click logic: Passing totalForks to detail screen
                        onClick = { onNavigateToDetail(uiState.totalForks) }
                    )
                }
            }
        }
    }
}

@Composable
fun UserHeader(user: GithubUser, totalForks: Int) {
    val isEliteUser = totalForks > 5000

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Using Coil for image loading (optimized bundle size)
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = user.name, style = MaterialTheme.typography.headlineSmall)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Total Forks: $totalForks",
                        color = if (isEliteUser) Color.Red else Color.Unspecified,
                        fontWeight = if (isEliteUser) FontWeight.Bold else FontWeight.Normal
                    )
                    if (isEliteUser) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star Badge",
                            tint = Color(0xFFFFD700), // Gold Color
                            modifier = Modifier.size(20.dp).padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(repo: GithubRepo, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(repo.name, fontWeight = FontWeight.Bold) },
        supportingContent = { Text(repo.description ?: "No description", maxLines = 2) },
        trailingContent = { Text("🍴 ${repo.forks}") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Handles row click
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.small)
    )
}