package com.rob729.newsfeed.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.rob729.newsfeed.utils.Constants.GRADIENT_START_COORDINATE

@Composable
fun LoadingShimmer() {

    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.7f), //darker grey (60% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (20% opacity)
        Color.LightGray.copy(alpha = 0.7f)
    )

    val transition = rememberInfiniteTransition() // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(GRADIENT_START_COORDINATE, GRADIENT_START_COORDINATE),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    ShimmerListItem(brush = brush)
}
