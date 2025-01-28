package com.hb.test.prensentation.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hb.test.R
import com.hb.test.prensentation.navigation.Screens
import com.hb.test.utils.LottieLoader

@Composable
fun SplashScreen(navController: NavController) {

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
                if (isProgressFinished) navController.navigate(Screens.HomeScreen.route) { popUpTo(0) }
            }
        }
    }
}
