package com.hb.test.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hb.test.presentation.features.details.SectionTitle
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SectionTitleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mySimpleTest() {
        composeTestRule.setContent { SectionTitle("Continue") }
        composeTestRule.onNodeWithText("Continue").assertIsDisplayed()
    }
}
