package com.mtfuji.sakura.openlibtest.ui.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme

@Composable
fun LoadingScreen(
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.6f)
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color,
            trackColor = trackColor,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OpenlibtestTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingScreen()
        }
    }
}