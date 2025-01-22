package com.mtfuji.sakura.openlibtest.feature.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mtfuji.sakura.openlibtest.R
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme
import com.mtfuji.sakura.openlibtest.ui.theme.dimens

@Composable
fun BookCard(
    bookModel: BookModel,
    onBookSelected: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(MaterialTheme.dimens.unit3)
            .clickable { onBookSelected(bookModel.key) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.unit2)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(MaterialTheme.dimens.unit18)
                    .width(MaterialTheme.dimens.unit18)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookModel.coverUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                contentDescription = bookModel.title,
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(R.drawable.outline_image_24),
                        contentDescription = bookModel.title
                    )
                },
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.unit2))
            Column(
                modifier = Modifier
            ) {
                Text(
                    modifier = Modifier,
                    text = bookModel.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.unit2))
                Text(
                    modifier = Modifier,
                    text = "By ${bookModel.authorNames.joinToString(separator = ", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
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