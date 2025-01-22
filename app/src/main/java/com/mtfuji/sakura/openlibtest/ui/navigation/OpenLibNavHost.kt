package com.mtfuji.sakura.openlibtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mtfuji.sakura.openlibtest.feature.home.HomeRoute
import com.mtfuji.sakura.openlibtest.feature.home.homeRoute

@Composable
fun OpenLibNavHost(
    appState: OpenLibAppState,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeRoute(
            onBookSelected = { bookKey ->
                appState.showBottomSheet(bookKey)
            }
        )
    }
}