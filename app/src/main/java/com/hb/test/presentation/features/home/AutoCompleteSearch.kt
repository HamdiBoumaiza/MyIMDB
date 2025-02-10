package com.hb.test.presentation.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.hb.test.R
import com.hb.test.domain.model.Movie
import com.hb.test.presentation.features.MovieItem
import com.hb.test.presentation.theme.dp_10
import com.hb.test.presentation.theme.dp_12
import com.hb.test.presentation.theme.dp_15
import com.hb.test.presentation.theme.dp_2
import com.hb.test.presentation.theme.dp_200
import com.hb.test.presentation.theme.dp_4
import com.hb.test.presentation.theme.dp_55
import com.hb.test.presentation.theme.sp_16
import com.hb.test.utils.HOME_SCREEN_SEARCH
import com.hb.test.utils.HOME_SCREEN_SEARCH_RESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoComplete(
    movieList: List<Movie>,
    onSelectMovies: (String) -> Unit,
    onMovieClicked: (Int) -> Unit,
) {
    var result = movieList
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var searchedMovieInput by remember { mutableStateOf("") }
    searchedMovieInput.useDebounce { searchParam -> onSelectMovies(searchParam) }

    val focusRequester = remember { FocusRequester() }

    var expanded by remember { mutableStateOf(false) }
    expanded = movieList.isNotEmpty()

    Column(
        modifier = Modifier
            .padding(dp_10)
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = searchedMovieInput,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dp_12))
                    .focusRequester(focusRequester)
                    .height(dp_55)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(dp_15),
                    ).testTag(HOME_SCREEN_SEARCH),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "SearchIcon",
                    )
                },
                onValueChange = { searchedMovieInput = it },
                trailingIcon = {
                    if (searchedMovieInput.isNotEmpty()) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clear text",
                            modifier = Modifier.clickable { searchedMovieInput = "" },
                        )
                    }
                },
                placeholder = { Text(stringResource(R.string.search_by_movie_title)) },
                colors =
                TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle =
                TextStyle(
                    color = Color.Black,
                    fontSize = sp_16,
                ),
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions =
                KeyboardActions(onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                singleLine = true,
            )

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = dp_2)
                        .fillMaxWidth(),
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(dp_4),
                        modifier =
                        Modifier
                            .heightIn(max = dp_200)
                            .background(MaterialTheme.colorScheme.background)
                            .testTag(HOME_SCREEN_SEARCH_RESULT),
                    ) {
                        if (searchedMovieInput.isNotEmpty()) {
                            item { Spacer(modifier = Modifier.height(dp_2)) }
                            items(result) { movie ->
                                MovieItem(movie = movie) {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    result = emptyList()
                                    onMovieClicked(movie.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> T.useDebounce(
    delayMillis: Long = 1000L,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onChange: (T) -> Unit,
): T {
    val state by rememberUpdatedState(this)
    DisposableEffect(state) {
        val job =
            coroutineScope.launch {
                delay(delayMillis)
                onChange(state)
            }
        onDispose {
            job.cancel()
        }
    }
    return state
}
