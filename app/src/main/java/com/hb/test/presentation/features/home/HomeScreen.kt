package com.hb.test.presentation.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hb.test.R
import com.hb.test.domain.model.Movie
import com.hb.test.presentation.theme.dp_2
import com.hb.test.presentation.theme.dp_20
import com.hb.test.utils.HOME_SCREEN
import com.hb.test.utils.LoopReverseLottieLoader
import com.hb.test.utils.mapError
import com.hb.test.utils.showToast
import com.hb.test.utils.trimTitle
import retrofit2.HttpException
import java.io.IOException

@Composable
fun HomeScreen(
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    onNavigateToFavoritesScreen: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .testTag(HOME_SCREEN),
        ) {
            val listState: LazyListState = rememberLazyListState()
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = dp_2)
                    .fillMaxSize()
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        val context = LocalContext.current
                        Box(modifier = Modifier.weight(1f)) {
                            val movies =
                                homeViewModel.searchMoviesResult.value.collectAsLazyPagingItems()
                            if (movies.loadState.refresh is LoadState.Error) {
                                context.showToast(
                                    stringResource((movies.loadState.refresh as LoadState.Error).error.mapError())
                                )
                            }
                            AutoComplete(
                                movieList = movies.itemSnapshotList.toList().filterNotNull(),
                                onSelectMovies = { movie -> homeViewModel.searchMovies(movie) },
                                onMovieClicked = { id -> onNavigateToDetailsScreen(id) }
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .padding(top = 25.dp)
                                .clickable { onNavigateToFavoritesScreen() },
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites Icon",
                        )
                    }
                }
                item {
                    MovieSection(
                        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
                        homeMovieType = HomeMovieType.TRENDING,
                        pagingItems = homeViewModel.trendingMoviesState.collectAsLazyPagingItems(),
                        onViewAllClick = {},
                        onErrorClick = { homeViewModel.getTrendingMovies() }
                    )
                }
                item {
                    MovieSection(
                        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
                        homeMovieType = HomeMovieType.POPULAR,
                        pagingItems = homeViewModel.popularMoviesState.collectAsLazyPagingItems(),
                        onViewAllClick = {},
                        onErrorClick = { homeViewModel.getPopularMovies() }
                    )
                }
                item {
                    MovieSection(
                        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
                        homeMovieType = HomeMovieType.TOP_RATED,
                        pagingItems = homeViewModel.topRatedMoviesState.value.collectAsLazyPagingItems(),
                        onViewAllClick = {},
                        onErrorClick = { homeViewModel.getTopRatedMovies() }
                    )
                }
                item {
                    MovieSection(
                        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
                        homeMovieType = HomeMovieType.UPCOMING,
                        pagingItems = homeViewModel.upcomingMoviesState.value.collectAsLazyPagingItems(),
                        onViewAllClick = {},
                        onErrorClick = { homeViewModel.getUpcomingMovies() }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieSection(
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    homeMovieType: HomeMovieType,
    pagingItems: LazyPagingItems<Movie>,
    onViewAllClick: () -> Unit,
    onErrorClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(dp_20))
    Row {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            text = stringResource(homeMovieType.getTitle()),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = Bold
        )
        Text(
            text = "View All",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable { onViewAllClick() }
        )
    }
    ScrollableMovieItems(onNavigateToDetailsScreen, pagingItems) { onErrorClick() }
}

private fun HomeMovieType.getTitle() = when (this.name) {
    HomeMovieType.TRENDING.name -> R.string.trending
    HomeMovieType.POPULAR.name -> R.string.popular
    HomeMovieType.TOP_RATED.name -> R.string.top_rated
    else -> R.string.upcoming
}

@Composable
private fun ScrollableMovieItems(
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    pagingItems: LazyPagingItems<Movie>,
    onErrorClick: () -> Unit
) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoopReverseLottieLoader(lottieFile = R.raw.loader)
            }

            is LoadState.NotLoading -> {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(pagingItems.itemCount) { index ->
                        MovieItem(
                            imageUrl = pagingItems[index]?.imageUrl ?: "",
                            title = pagingItems[index]?.title ?: ""
                        ) {
                            pagingItems[index]?.let { onNavigateToDetailsScreen(it.id) }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                Box(
                    contentAlignment = Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clickable { onErrorClick() }
                ) {
                    Text(
                        text = getErrorMessage(pagingItems.loadState.refresh as LoadState.Error),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = Light,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun getErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is HttpException -> stringResource(R.string.sorry_something_went_wrong_tap_to_retry)
        is IOException -> stringResource(R.string.connection_failed_tap_to_retry)
        else -> stringResource(R.string.failed_tap_to_retry)
    }
}

@Composable
private fun MovieItem(
    imageUrl: String,
    title: String,
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { onclick() },
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(140.dp)
                .height(200.dp),
            contentDescription = ""
        )
        Text(
            text = title.trimTitle(),
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth(),
            maxLines = 1,
            color = MaterialTheme.colorScheme.secondary,
            overflow = TextOverflow.Ellipsis,
            fontWeight = Normal,
            fontSize = 12.sp,
            textAlign = TextAlign.Start
        )
    }
}
