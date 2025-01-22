package com.mtfuji.sakura.openlibtest.feature.bookdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtfuji.sakura.openlibtest.ui.UiState
import io.reactivex.rxjava3.core.BackpressureStrategy
import kotlinx.coroutines.reactive.asFlow

@Composable
fun BookDetailsRoute(
    bookKey: String,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val uiStateFlow = viewModel.uiState.toFlowable(BackpressureStrategy.LATEST).asFlow()
    val uiState by uiStateFlow.collectAsState(initial = UiState.Loading)

    viewModel.saveBookKey(bookKey)

    BookDetailsScreen(
        uiState = uiState,
        onErrorRetry = viewModel::onErrorRetry
    )
}