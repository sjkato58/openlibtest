package com.mtfuji.sakura.openlibtest.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.feature.home.composable.BookCard
import com.mtfuji.sakura.openlibtest.ui.UiState
import com.mtfuji.sakura.openlibtest.ui.composeable.LoadingScreen

@Composable
fun HomeScreen(
    uiState: UiState<List<BookModel>>,
    onBookSelected: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is UiState.Success<List<BookModel>> -> {
                ContentDisplay(uiState, onBookSelected)
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
    uiState: UiState.Success<List<BookModel>>,
    onBookSelected: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(uiState.data) {
            BookCard(
                bookModel = it,
                onBookSelected = onBookSelected
            )
        }
    }
}