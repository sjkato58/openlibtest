package com.mtfuji.sakura.openlibtest.feature.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme

@Composable
fun BookCard(
    bookModel: BookModel,
    onBookSelected: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .clickable { onBookSelected(bookModel.key) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(72.dp)
                    .width(72.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookModel.coverUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                contentDescription = bookModel.title,
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    modifier = Modifier,
                    text = bookModel.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier,
                    text = "By ${bookModel.authorNames.joinToString(separator = ", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    OpenlibtestTheme {
        BookCard(
            bookModel = BookModel(
                title = "A Title",
                authorNames = listOf("An Author", "A.N. Author"),
                key = "A Key",
                coverUrl = ""
            ),
            onBookSelected = {}
        )
    }
}