package com.hb.test.presentation.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hb.test.presentation.navigation.NavGraph
import com.hb.test.presentation.theme.MyTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeContent),
                    color = MaterialTheme.colorScheme.background
                ) { NavGraph(navController = rememberNavController()) }
            }
        }
    }
}
