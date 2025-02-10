package com.hb.test.presentation.features.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hb.test.R
import com.hb.test.presentation.features.MovieItem
import com.hb.test.presentation.theme.dp_10
import com.hb.test.presentation.theme.dp_12
import com.hb.test.presentation.theme.dp_30
import com.hb.test.presentation.theme.dp_4
import com.hb.test.presentation.theme.dp_8
import com.hb.test.presentation.theme.sp_14
import com.hb.test.presentation.theme.sp_20

@Composable
fun FavoritesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteMoviesState by favoritesViewModel.favoriteMoviesState.collectAsStateWithLifecycle()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dp_8),
            verticalArrangement = Arrangement.spacedBy(dp_12)
        ) {
            Spacer(Modifier.height(dp_8))
            Image(
                modifier = Modifier
                    .height(dp_30)
                    .width(dp_30)
                    .clickable { onNavigateBack() },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.favorite_movies),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = sp_20,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dp_10))
            if (favoriteMoviesState.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.no_favorite_movies_added_yet),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = sp_14,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(dp_4),
                ) {
                    items(favoriteMoviesState) { movie ->
                        MovieItem(movie) { onNavigateToDetailsScreen(movie.id) }
                    }
                }
            }
        }
    }
}
