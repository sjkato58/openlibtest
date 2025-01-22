package com.mtfuji.sakura.openlibtest.ui.composeable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mtfuji.sakura.openlibtest.feature.bookdetails.BookDetailsRoute
import com.mtfuji.sakura.openlibtest.ui.navigation.OpenLibAppState
import io.reactivex.rxjava3.core.BackpressureStrategy
import kotlinx.coroutines.reactive.asFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenLibBottomSheet(
    appState: OpenLibAppState
) {
    val bottomSheetState by appState.bottomSheetState.toFlowable(BackpressureStrategy.LATEST)
        .asFlow()
        .collectAsState(initial = Pair(false, null))

    val (isVisible, sheetKey) = bottomSheetState

    if (isVisible && sheetKey.isNullOrBlank().not()) {
        ModalBottomSheet(
            onDismissRequest = { appState.hideBottomSheet() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BookDetailsRoute(
                bookKey = sheetKey ?: ""
            )
        }
    }
}