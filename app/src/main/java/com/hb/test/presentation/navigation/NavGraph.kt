package com.hb.test.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hb.test.presentation.features.SplashScreen
import com.hb.test.presentation.features.details.DetailsScreen
import com.hb.test.presentation.features.details.DetailsViewModel
import com.hb.test.presentation.features.favorites.FavoritesScreen
import com.hb.test.presentation.features.favorites.FavoritesViewModel
import com.hb.test.presentation.features.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route,
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        exitTransition = { exitTransition() },
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        /** Welcome Screen */
        composable(route = Screens.WelcomeScreen.route) {
            SplashScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(
                onNavigateToDetailsScreen = { movieId ->
                    navController.navigate(
                        Screens.DetailsScreen.withMovie(movieId)
                    )
                },
                onNavigateToFavoritesScreen = { navController.navigate(Screens.FavoritesScreen.route) }
            )
        }

        composable(route = Screens.FavoritesScreen.route) {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val favoriteMoviesState by viewModel.favoriteMoviesState.collectAsStateWithLifecycle()

            FavoritesScreen(
                favoriteMoviesState = favoriteMoviesState,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetailsScreen = { movieId ->
                    navController.navigate(Screens.DetailsScreen.withMovie(movieId))
                }
            )
        }

        composable(
            route = Screens.DetailsScreen.route,
            arguments = listOf(navArgument(MOVIE_ID_ARG_KEY) { type = NavType.IntType }),
        ) { navBackStackEntry ->
            val viewModel: DetailsViewModel = hiltViewModel()
            val movieID = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARG_KEY) ?: 0
            DetailsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetailsScreen = { movieId ->
                    navController.navigate(
                        Screens.DetailsScreen.withMovie(movieId)
                    )
                },
                movieId = movieID,
                onEvent = viewModel::onEvent,
            )
        }
    }
}
