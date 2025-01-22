package com.mtfuji.sakura.openlibtest.feature.bookdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mtfuji.sakura.openlibtest.R
import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import com.mtfuji.sakura.openlibtest.ui.UiState
import com.mtfuji.sakura.openlibtest.ui.composeable.ErrorScreen
import com.mtfuji.sakura.openlibtest.ui.composeable.LoadingScreen
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme
import com.mtfuji.sakura.openlibtest.ui.theme.dimens

@Composable
fun BookDetailsScreen(
    uiState: UiState<BookDetailsModel>,
    onErrorRetry: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is UiState.Success<BookDetailsModel> -> {
                ContentDisplay(uiState)
            }
            is UiState.Error -> {
                BookDetailsErrorScreen(
                    errorMessage = uiState.message,
                    onErrorRetry = onErrorRetry
                )
            }
            is UiState.Loading -> {
                BookDetailsLoadingScreen()
            }
        }
    }
}

@Composable
fun ContentDisplay(
    uiState: UiState.Success<BookDetailsModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.data.title,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.unit2),
                text = "By ${uiState.data.authors.joinToString(separator = ", ")}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "First publish in ${uiState.data.firstPublishedData}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Description:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.unit2),
                text = uiState.data.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Teaser:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.unit2),
                text = uiState.data.firstSentence,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (uiState.data.subjects.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Subjects Covered:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = MaterialTheme.dimens.unit2),
                    text = uiState.data.subjects.joinToString(separator = ", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        if (uiState.data.covers.isNotEmpty()) {
            item {
                BookDetailsCovers(uiState.data.title, uiState.data.covers)
            }
        }
    }
}

@Composable
fun LazyItemScope.BookDetailsCovers(
    title: String,
    covers: List<String>
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Covers of the book",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.unit3))
    Row(
        modifier = Modifier
            .height(MaterialTheme.dimens.unit18),
        horizontalArrangement = Arrangement.Start
    ) {
        for (imageUrl in covers) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(MaterialTheme.dimens.unit18)
                    .width(MaterialTheme.dimens.unit18)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(R.drawable.outline_image_24),
                        contentDescription = title
                    )
                },
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.unit2))
        }
    }
}

@Composable
fun BookDetailsErrorScreen(
    errorMessage: String,
    onErrorRetry: () -> Unit
) {
    ErrorScreen(
        errorMessage = errorMessage,
        onRetry = onErrorRetry
    )
}

@Composable
fun BookDetailsLoadingScreen() {
    LoadingScreen()
}

@Preview
@Composable
private fun Preview() {
    OpenlibtestTheme {
        Surface {
            OpenlibtestTheme {
                BookDetailsScreen(
                    uiState = UiState.Success(
                        BookDetailsModel(
                            key = "A Key",
                            title = "This is a Title",
                            firstPublishedData = "September 9, 2005",
                            authors = listOf("An Author", "A.N. Author"),
                            description = "This is a description, you will never be too sure.",
                            covers = listOf(
                                "https://covers.openlibrary.org/b/6424160-M.jpg",
                                "https://covers.openlibrary.org/b/6424160-M.jpg",
                                "https://covers.openlibrary.org/b/6424160-M.jpg"
                            ),
                            subjects = listOf(
                                "Business",
                                "Nonfiction",
                                "Audiobooks",
                            ),
                            firstSentence = "This is the first sentence",
                            subjectTimes = "Ao longo de toda história",
                            latestRevision = 6,
                        )
                    ),
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
            BookDetailsScreen(
                uiState = UiState.Error("Something went wrong!"),
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
            BookDetailsScreen(
                uiState = UiState.Loading,
                onErrorRetry = { }
            )
        }
    }
}