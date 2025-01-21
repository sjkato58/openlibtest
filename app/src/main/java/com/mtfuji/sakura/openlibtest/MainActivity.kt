package com.mtfuji.sakura.openlibtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mtfuji.sakura.openlibtest.ui.MainScreen
import com.mtfuji.sakura.openlibtest.ui.navigation.rememberOpenLibAppState
import com.mtfuji.sakura.openlibtest.ui.theme.OpenlibtestTheme

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