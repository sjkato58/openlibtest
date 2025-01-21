package com.mtfuji.sakura.openlibtest.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.mtfuji.sakura.openlibtest.R
import com.mtfuji.sakura.openlibtest.feature.home.HomeRoute
import kotlin.reflect.KClass

enum class DestinationNavData(
    @StringRes val titleTextId: Int,
    val route: KClass<*>
) {
    HOME(
        titleTextId = R.string.nav_data_home,
        route = HomeRoute::class
    )
}

fun NavDestination?.getCurrentNavDestination(): DestinationNavData? =
    DestinationNavData.entries.firstOrNull { destinationNavData ->
        this?.hasRoute(route = destinationNavData.route) == true
    }