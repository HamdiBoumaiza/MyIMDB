package com.hb.test.cucumber

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.hb.test.domain.model.Movie
import com.hb.test.prensentation.features.home.AutoComplete
import com.hb.test.utils.HOME_SCREEN_SEARCH
import com.hb.test.utils.HOME_SCREEN_SEARCH_RESULT
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class SearchSteps {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Given("^I initialize The autoComplete widget$")
    fun theHomeScreenIsDisplayedToTheCustomer() {
        composeTestRule.setContent {
            AutoComplete(
                listOf(movie, movie.copy(title = "Hello"), movie.copy(title = "Hitch")),
                {},
                {}
            )
        }
        composeTestRule.onNodeWithTag(HOME_SCREEN_SEARCH).assertIsDisplayed()
    }

    @When("^I Search for the Movie \"([^\"]*)\"$")
    fun theUserEntersSearchInput(title: String) {
        composeTestRule.onNodeWithTag(HOME_SCREEN_SEARCH).performTextInput(title)
        composeTestRule.mainClock.advanceTimeBy(1500)
        composeTestRule.onNodeWithTag(HOME_SCREEN_SEARCH).assertExists()
            .assert(hasText(title))
    }

    @And("^Click on the first search result$")
    fun theCustomerRequestsToLogin() {
        composeTestRule.onNodeWithTag(HOME_SCREEN_SEARCH_RESULT).performScrollToIndex(0).performClick()
    }

    @Then("^Make sure the first item in the result list is \"([^\"]*)\"$")
    fun showDetails(title: String) {
        composeTestRule.onNodeWithTag(HOME_SCREEN_SEARCH_RESULT).performScrollToNode(hasText(title))
    }

    companion object {
        val movie = Movie(
            backdropPath = "",
            imageUrl = "",
            genres = emptyList(),
            id = 0,
            originalLanguage = "",
            overview = "",
            releaseDate = "",
            title = "The Godfather",
            voteAverage = 0.0,
            voteCount = 0
        )
    }
}
