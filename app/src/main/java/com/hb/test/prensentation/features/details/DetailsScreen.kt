package com.hb.test.prensentation.features.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hb.test.R
import com.hb.test.domain.model.Cast
import com.hb.test.domain.model.Genre
import com.hb.test.domain.model.Movie
import com.hb.test.prensentation.features.home.trimTitle
import com.hb.test.prensentation.navigation.Screens
import com.hb.test.prensentation.theme.dp_10
import com.hb.test.prensentation.theme.dp_100
import com.hb.test.prensentation.theme.dp_12
import com.hb.test.prensentation.theme.dp_150
import com.hb.test.prensentation.theme.dp_2
import com.hb.test.prensentation.theme.dp_30
import com.hb.test.prensentation.theme.dp_300
import com.hb.test.prensentation.theme.dp_4
import com.hb.test.prensentation.theme.dp_5
import com.hb.test.prensentation.theme.dp_8
import com.hb.test.prensentation.theme.sp_12
import com.hb.test.prensentation.theme.sp_16
import com.hb.test.prensentation.theme.sp_18
import com.hb.test.prensentation.theme.sp_20
import com.hb.test.utils.DETAILS_SCREEN_TITLE
import com.hb.test.utils.DarkAndLightPreviews
import com.hb.test.utils.LoopReverseLottieLoader
import com.hb.test.utils.mapError
import com.hb.test.utils.showToast

@Composable
fun DetailsScreen(
    navController: NavController,
    movieId: Int,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    detailsViewModel.setMovieId(movieId)
    val movieState by detailsViewModel.movieDetails.collectAsState()
    val castState by detailsViewModel.movieCast.collectAsStateWithLifecycle()
    val similarMovies = detailsViewModel.similarMovies.collectAsLazyPagingItems()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dp_8),
            verticalArrangement = Arrangement.spacedBy(dp_12)
        ) {
            MovieDetails(navController, movieState, detailsViewModel)
            MovieCast(castState)
            RecommendedMovie(similarMovies, navController)
        }
    }
}

@Composable
private fun MovieCast(castState: DetailsScreenCastUIState) {
    when (castState) {
        DetailsScreenCastUIState.Loading -> LoopReverseLottieLoader(lottieFile = R.raw.loader)
        is DetailsScreenCastUIState.Error -> ErrorText(stringResource(castState.messageRes))
        is DetailsScreenCastUIState.Success -> {
            if (castState.listCast.isNotEmpty()) MovieCastList(castState.listCast)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetails(
    navController: NavController,
    state: DetailsScreenUIState,
    detailsViewModel: DetailsViewModel,
) {
    val isMovieAlreadyFavorite = detailsViewModel.isFavoriteMovie.collectAsState().value

    when (state) {
        DetailsScreenUIState.Loading -> LoopReverseLottieLoader(lottieFile = R.raw.loader)
        is DetailsScreenUIState.Error -> ErrorText(stringResource(state.messageRes))
        is DetailsScreenUIState.Success -> {
            Spacer(Modifier.height(dp_4))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .height(dp_30)
                        .width(dp_30)
                        .weight(0.1f)
                        .clickable { navController.popBackStack() },
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(DETAILS_SCREEN_TITLE),
                        text = state.movie.title,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = sp_20,
                        fontWeight = FontWeight.Bold
                    )
                }

                val context = LocalContext.current
                Image(
                    modifier = Modifier
                        .height(dp_30)
                        .width(dp_30)
                        .weight(0.1f)
                        .clickable {
                            if (isMovieAlreadyFavorite) detailsViewModel.onEvent(
                                DetailUiEvent.DeleteFavoriteMovie(
                                    state.movie.id
                                )
                            )
                            else detailsViewModel.onEvent(DetailUiEvent.AddToFavMovies(state.movie))
                            context.showToast(
                                if (isMovieAlreadyFavorite) context.getString(R.string.fav_deleted)
                                else context.getString(R.string.fav_added)
                            )
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                    painter = painterResource(
                        id = if (isMovieAlreadyFavorite) R.drawable.ic_favorite_filled
                        else R.drawable.ic_favorite
                    ),
                    contentDescription = null
                )
            }
            AsyncImage(
                model = state.movie.imageUrl,
                modifier = Modifier
                    .clip(RoundedCornerShape(dp_12))
                    .fillMaxWidth()
                    .height(dp_300),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(android.R.drawable.ic_media_play),
                contentDescription = ""
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Released Date : ${state.movie.releaseDate}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = sp_16,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${state.movie.voteAverage} (${state.movie.voteCount})",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = sp_16,
                    fontWeight = FontWeight.SemiBold
                )
            }
            SubtitleSecondary(text = state.movie.overview)
            ShowGenres(state.movie.genres)
        }
    }
}

@Composable
fun MovieCastList(cast: List<Cast>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionTitle(stringResource(R.string.cast))
        LazyRow(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            items(cast.size, itemContent = { index ->
                Column(
                    modifier = Modifier.padding(dp_5),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = cast[index].imageUrl,
                        modifier = Modifier
                            .clip(RoundedCornerShape(dp_12))
                            .width(dp_100)
                            .height(dp_150),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(dp_4))
                    SubtitleSecondary(text = cast[index].name.trimTitle(12))
                }
            })
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = sp_18,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun SubtitleSecondary(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(color = Color.Red)
    )
}

@Composable
fun RecommendedMovie(pagingItems: LazyPagingItems<Movie>, navigator: NavController) {
    Column(modifier = Modifier.padding(bottom = dp_10)) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoopReverseLottieLoader(lottieFile = R.raw.loader)
            }

            is LoadState.NotLoading -> {
                if (pagingItems.itemSnapshotList.isNotEmpty()) {
                    SectionTitle(stringResource(R.string.similar))
                    LazyRow(modifier = Modifier.fillMaxHeight()) {
                        items(pagingItems.itemSnapshotList) { recommended ->
                            Column(
                                modifier = Modifier.padding(dp_5)
                            ) {
                                AsyncImage(
                                    model = recommended?.imageUrl ?: "",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(dp_12))
                                        .width(dp_100)
                                        .height(dp_150)
                                        .clickable {
                                            recommended?.id?.let {
                                                navigator.navigate(
                                                    Screens.DetailsScreen.withMovie(it)
                                                )
                                            }
                                        },
                                    contentScale = ContentScale.Crop,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                ErrorText(stringResource((pagingItems.loadState.refresh as LoadState.Error).error.mapError()))
            }
        }
    }
}

@ExperimentalLayoutApi
@Composable
private fun ShowGenres(genres: List<Genre>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        FlowRow(modifier = Modifier.padding(dp_8), horizontalArrangement = Arrangement.Center) {
            genres.forEach { Chip(it.name) }
        }
    }
}

@Composable
private fun Chip(name: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .padding(dp_2)
            .selectable(selected = false, onClick = {})
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = sp_12,
            fontWeight = FontWeight.Normal
        )
    }
}

@ExperimentalLayoutApi
@DarkAndLightPreviews
@Composable
private fun PreviewMovieCast() {
    Column(
        verticalArrangement = Arrangement.spacedBy(dp_12)
    ) {
        ErrorText("this is a test for the ErrorText widget")
        SubtitleSecondary("this is a test for the SubtitleSecondary widget")
        SectionTitle("this is a test for the SectionTitle widget")
        ShowGenres(
            listOf(
                Genre(1, "Action"),
                Genre(2, "Animation"),
                Genre(3, "Drama"),
                Genre(4, "Thriller"),
            )
        )
        MovieCast(DetailsScreenCastUIState.Loading)
        MovieCast(DetailsScreenCastUIState.Error(R.string.error_server_please_try_again))
        MovieCast(
            DetailsScreenCastUIState.Success(
                listCast = listOf(
                    Cast("Hello"),
                    Cast("Hola")
                )
            )
        )
    }
}
