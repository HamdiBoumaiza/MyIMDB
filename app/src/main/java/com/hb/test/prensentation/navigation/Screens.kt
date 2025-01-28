package com.hb.test.prensentation.navigation

const val MOVIE_ID_ARG_KEY = "movie"

sealed class Screens(val route: String) {

    data object WelcomeScreen : Screens("welcome_screen")
    data object HomeScreen : Screens("home_screen")
    data object FavoritesScreen : Screens("favorites_screen")
    data object DetailsScreen : Screens("details_screen/{$MOVIE_ID_ARG_KEY}") {
        fun withMovie(movieId: Int): String {
            return route.replace("{$MOVIE_ID_ARG_KEY}", movieId.toString())
        }
    }
}
