package com.mtfuji.sakura.openlibtest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mtfuji.sakura.openlibtest.ui.composeable.OpenLibBottomSheet
import com.mtfuji.sakura.openlibtest.ui.navigation.OpenLibAppState
import com.mtfuji.sakura.openlibtest.ui.navigation.OpenLibNavHost
import com.mtfuji.sakura.openlibtest.ui.navigation.rememberOpenLibAppState
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme

@Composable
fun MainScreen(
    appState: OpenLibAppState
) {
    Surface(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MainTopAppBar(appState)
            }
        ) { innerPadding ->
            OpenLibNavHost(
                appState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
    OpenLibBottomSheet(appState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    appState: OpenLibAppState
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = appState.getCurrentTitle()
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    OpenlibtestTheme {
        val appState = rememberOpenLibAppState()
        MainScreen(appState)
    }
}