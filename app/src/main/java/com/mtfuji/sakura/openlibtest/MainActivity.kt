package com.mtfuji.sakura.openlibtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.mtfuji.sakura.openlibtest.ui.MainScreen
import com.mtfuji.sakura.openlibtest.ui.navigation.rememberOpenLibAppState
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenlibtestTheme {
                val appState = rememberOpenLibAppState()
                MainScreen(appState)
            }
        }
    }
}