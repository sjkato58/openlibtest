package com.mtfuji.sakura.openlibtest.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mtfuji.sakura.openlibtest.ui.UiState
import io.reactivex.rxjava3.core.BackpressureStrategy
import kotlinx.coroutines.reactive.asFlow
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavGraphBuilder.homeRoute(
    onBookSelected: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(onBookSelected, onBackPressed)
    }
}

@Composable
fun HomeRoute(
    onBookSelected: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiStateFlow = viewModel.uiState.toFlowable(BackpressureStrategy.LATEST).asFlow()
    val uiState by uiStateFlow.collectAsState(initial = UiState.Loading)

    HomeScreen(
        uiState = uiState,
        onBookSelected = onBookSelected,
        onErrorRetry = viewModel::fetchCurrentlyReadingBooks,
        onBackPressed = onBackPressed
    )
}