package com.rob729.newsfeed.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.rob729.newsfeed.utils.ScreenType

@Composable
fun LoadingView(screenType: ScreenType = ScreenType.HOME) {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.8f), //darker grey (60% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (20% opacity)
        Color.LightGray.copy(alpha = 0.8f)
    )

    val transition =
        rememberInfiniteTransition(label = "loading_shimmer_transition") // animate infinite times

    val translateAnimation by transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = LinearEasing
            )
        ), label = "loading_shimmer_translate_anim"
    )

    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(
            x = translateAnimation + 200f,
            y = translateAnimation + 200f
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (screenType) {
            ScreenType.HOME -> {
                repeat(times = 4) {
                    item {
                        NewsFeedItemShimmer(brush)
                    }
                }
            }

            ScreenType.SEARCH -> {
                repeat(times = 7) {
                    item {
                        SearchResultShimmer(brush)
                    }
                }
            }
        }
    }
}
