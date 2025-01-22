package com.mtfuji.sakura.openlibtest.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.feature.home.composable.BookCard
import com.mtfuji.sakura.openlibtest.ui.UiState
import com.mtfuji.sakura.openlibtest.ui.composeable.ErrorScreen
import com.mtfuji.sakura.openlibtest.ui.composeable.LoadingScreen
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme

@Composable
fun HomeScreen(
    uiState: UiState<List<BookModel>>,
    onBookSelected: (String) -> Unit,
    onErrorRetry: () -> Unit
) {
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
                HomeErrorScreen(
                    errorMessage = uiState.message,
                    onErrorRetry = onErrorRetry
                )
            }
            is UiState.Loading -> {
                HomeLoadingScreen()
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

@Composable
fun HomeErrorScreen(
    errorMessage: String,
    onErrorRetry: () -> Unit
) {
    ErrorScreen(
        errorMessage = errorMessage,
        onRetry = onErrorRetry
    )
}

@Composable
fun HomeLoadingScreen() {
    LoadingScreen()
}

@Preview
@Composable
private fun Preview() {
    OpenlibtestTheme {
        Surface {
            val list = listOf(
                BookModel(
                    title = "A Title",
                    key = "A Key",
                    coverUrl = "A cover Url",
                    authorNames = listOf("An Author")
                )
            )
            OpenlibtestTheme {
                HomeScreen(
                    uiState = UiState.Success(list),
                    onBookSelected = { },
                    onErrorRetry = { }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewError() {
    OpenlibtestTheme {
        Surface {
            HomeScreen(
                uiState = UiState.Error("Something went wrong!"),
                onBookSelected = { },
                onErrorRetry = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    OpenlibtestTheme {
        Surface {
            HomeScreen(
                uiState = UiState.Loading,
                onBookSelected = { },
                onErrorRetry = { }
            )
        }
    }
}