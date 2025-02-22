package com.mtfuji.sakura.openlibtest.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberOpenLibAppState(
    navController: NavHostController = rememberNavController(),
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
): OpenLibAppState {
    return remember(
        navController
    ) {
        OpenLibAppState(
            navController,
            sheetState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class OpenLibAppState(
    val navController: NavHostController,
    val sheetState: SheetState
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentDestinationNavData: DestinationNavData?
        @Composable get() = currentDestination.getCurrentNavDestination()

    @Composable
    fun getCurrentTitle(): String = currentDestinationNavData?.let {
        stringResource(it.titleTextId)
    } ?: ""

    private val _bottomSheetState: BehaviorSubject<Pair<Boolean, String?>> = BehaviorSubject.createDefault(Pair(false, null))
    val bottomSheetState: Observable<Pair<Boolean, String?>> = _bottomSheetState.hide()

    fun showBottomSheet(key: String) {
        _bottomSheetState.onNext(Pair(true, key))
    }

    fun hideBottomSheet() {
        _bottomSheetState.onNext(Pair(false, null))
    }

    fun navigateBack() = navController.navigateUp()
}