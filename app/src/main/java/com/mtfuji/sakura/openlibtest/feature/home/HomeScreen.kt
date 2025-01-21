package com.mtfuji.sakura.openlibtest.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.ui.UiState
import com.mtfuji.sakura.openlibtest.ui.composeable.LoadingScreen

@Composable
fun HomeScreen(
    uiState: UiState<List<BookModel>>,
    onEnterClicked: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is UiState.Success<List<BookModel>> -> {
                ContentDisplay(uiState, onEnterClicked)
            }
            is UiState.Error -> {

            }
            is UiState.Loading -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun ContentDisplay(
    uiState: UiState<List<BookModel>>,
    onEnterClicked: () -> Unit,
) {

}