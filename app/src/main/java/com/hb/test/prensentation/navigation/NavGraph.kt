package com.hb.test.prensentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hb.test.prensentation.features.SplashScreen
import com.hb.test.prensentation.features.details.DetailsScreen
import com.hb.test.prensentation.features.favorites.FavoritesScreen
import com.hb.test.prensentation.features.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)

    ) {
        /** Welcome Screen */
        composable(
            route = Screens.WelcomeScreen.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
        ) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Screens.HomeScreen.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screens.FavoritesScreen.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
        ) {
            FavoritesScreen(navController = navController)
        }

        composable(
            route = Screens.DetailsScreen.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            arguments = listOf(navArgument(MOVIE_ID_ARG_KEY) { type = NavType.IntType }),
        ) { navBackStackEntry ->
            val movieID = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARG_KEY) ?: 0
            DetailsScreen(navController = navController, movieId = movieID)
        }
    }
}
