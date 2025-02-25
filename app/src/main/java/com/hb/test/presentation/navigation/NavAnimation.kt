package com.hb.test.presentation.navigation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

// Duration of the navigation animation
private const val NAVIGATION_ANIM_DURATION = 300

/**
 * Enter transition for the navigation animation
 */
fun enterTransition() = slideInHorizontally(
    initialOffsetX = { NAVIGATION_ANIM_DURATION },
    animationSpec = tween(
        durationMillis = (NAVIGATION_ANIM_DURATION * 1.5).toInt(),
        easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f)
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = NAVIGATION_ANIM_DURATION,
        delayMillis = NAVIGATION_ANIM_DURATION / 4,
        easing = LinearOutSlowInEasing
    )
)

/**
 * Exit transition for the navigation animation
 */
fun exitTransition() = slideOutHorizontally(
    targetOffsetX = { -NAVIGATION_ANIM_DURATION },
    animationSpec = tween(
        durationMillis = (NAVIGATION_ANIM_DURATION * 1.5).toInt(),
        easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f)
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = NAVIGATION_ANIM_DURATION,
        delayMillis = NAVIGATION_ANIM_DURATION / 4,
        easing = LinearOutSlowInEasing
    )
)

/**
 * Enter transition for the pop navigation animation
 */
fun popEnterTransition() = slideInHorizontally(
    initialOffsetX = { -NAVIGATION_ANIM_DURATION },
    animationSpec = tween(
        durationMillis = (NAVIGATION_ANIM_DURATION * 1.2).toInt(),
        easing = CubicBezierEasing(0.6f, 0.05f, 0.19f, 0.95f)
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = NAVIGATION_ANIM_DURATION / 2,
        delayMillis = NAVIGATION_ANIM_DURATION / 4,
        easing = LinearEasing
    )
)

/**
 * Exit transition for the pop navigation animation
 */
fun popExitTransition() = slideOutHorizontally(
    targetOffsetX = { NAVIGATION_ANIM_DURATION },
    animationSpec = tween(
        durationMillis = (NAVIGATION_ANIM_DURATION * 1.2).toInt(),
        easing = CubicBezierEasing(0.6f, 0.05f, 0.19f, 0.95f)
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = NAVIGATION_ANIM_DURATION / 2,
        delayMillis = NAVIGATION_ANIM_DURATION / 4,
        easing = LinearEasing
    )
)
