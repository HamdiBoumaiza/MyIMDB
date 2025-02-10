package com.hb.test.presentation.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hb.test.R
import com.hb.test.utils.LottieLoader

@Composable
fun SplashScreen(onNavigateToHomeScreen: () -> Unit) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieLoader(lottieFile = R.raw.intro_animation) { isProgressFinished ->
                if (isProgressFinished) onNavigateToHomeScreen()
            }
        }
    }
}
