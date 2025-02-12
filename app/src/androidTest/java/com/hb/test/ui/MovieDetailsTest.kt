package com.hb.test.ui

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hb.test.domain.model.Movie
import com.hb.test.presentation.features.MainActivity
import com.hb.test.presentation.features.details.DetailsScreenUIState
import com.hb.test.presentation.features.details.DetailsViewModel
import com.hb.test.presentation.features.details.MovieDetails
import com.hb.test.utils.DETAILS_SCREEN_TITLE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieDetailsTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            MovieDetails(
                onNavigateBack = {},
                DetailsScreenUIState.Success(Movie().copy(title = TITLE_TEST)),
                onEvent = {},
                composeTestRule.activity.viewModels<DetailsViewModel>().value
            )
        }
    }

    @Test
    fun details_screen_test() {
        composeTestRule.onNodeWithTag(DETAILS_SCREEN_TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DETAILS_SCREEN_TITLE).assertExists()
            .assert(hasText(TITLE_TEST))
    }

    companion object {
        const val TITLE_TEST = "Hello"
    }
}
