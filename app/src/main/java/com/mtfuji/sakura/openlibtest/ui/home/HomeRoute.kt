package com.mtfuji.sakura.openlibtest.ui.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavGraphBuilder.homeRoute(
    onEnterClicked: () -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(onEnterClicked)
    }
}

@Composable
fun HomeRoute(
    onEnterClicked: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    HomeScreen(
        onEnterClicked = onEnterClicked
    )
}