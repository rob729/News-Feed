package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rob729.newsfeed.R
import com.rob729.newsfeed.model.ui.IconData
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

private const val LEFT_IMAGE_SCALE_FACTOR = 2.2f

@Composable
fun Toolbar(
    title: String,
    leftIcon: IconData? = null,
    titleSuffixImage: Painter? = null,
    rightIcon1: IconData? = null,
    rightIcon2: IconData? = null,
    toolbarElevation: Dp,
    overflowMenuContent: @Composable ((
        isOverflowMenuExpanded: Boolean,
        dismissOverflowMenu: () -> Unit
    ) -> Unit)? = null,
) {

    var isOverflowMenuExpanded by remember { mutableStateOf(false) }

    Surface(tonalElevation = toolbarElevation, color = colorResource(R.color.status_bar)) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            val (titleConstraint, leftIconConstraint, titleSuffixImageConstraint,
                rightIcon1Constraint, rightIcon2Constraint, overflowMenuContentConstraint) = createRefs()
            leftIcon?.let {
                Icon(
                    imageVector = it.icon,
                    contentDescription = "left_icon",
                    tint = Color.White,
                    modifier = Modifier
                        .constrainAs(leftIconConstraint) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(end = 8.dp)
                        .clickable(onClick = it.clickAction)
                )
            }

            Text(
                text = title,
                modifier = Modifier
                    .constrainAs(titleConstraint) {
                        start.linkTo(leftIconConstraint.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 4.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = lexendDecaFontFamily,
                color = Color.White
            )

            titleSuffixImage?.let {
                Image(
                    painter = it,
                    contentDescription = "left_image",
                    modifier = Modifier
                        .size(24.dp)
                        .scale(LEFT_IMAGE_SCALE_FACTOR)
                        .constrainAs(titleSuffixImageConstraint) {
                            start.linkTo(titleConstraint.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        })
            }

            rightIcon2?.let {
                IconButton(modifier = Modifier
                    .constrainAs(rightIcon2Constraint) {
                        end.linkTo(rightIcon1Constraint.start)
                        top.linkTo(rightIcon1Constraint.top)
                        bottom.linkTo(rightIcon1Constraint.bottom)
                    }
                    .padding(end = 12.dp)
                    .size(28.dp)
                    .testTag("right_icon_2"), onClick = it.clickAction) {
                    Icon(
                        imageVector = it.icon, contentDescription = "right_icon_2",
                        tint = Color.White
                    )
                }
            }

            rightIcon1?.let {
                IconButton(modifier = Modifier
                    .constrainAs(rightIcon1Constraint) {
                        end.linkTo(overflowMenuContentConstraint.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(28.dp)
                    .testTag("right_icon_1"), onClick = it.clickAction
                ) {

                    Icon(
                        imageVector = it.icon,
                        contentDescription = "right_icon_1",
                        tint = Color.White
                    )
                }
            }

            overflowMenuContent?.let {
                IconButton(modifier = Modifier
                    .constrainAs(overflowMenuContentConstraint) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(28.dp)
                    .testTag("overflow_menu_icon"),
                    onClick = { isOverflowMenuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White,
                    )

                    overflowMenuContent(isOverflowMenuExpanded) {
                        isOverflowMenuExpanded = false
                    }
                }
            }

        }
    }
}
