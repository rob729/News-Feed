package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun RegularPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = title,
                fontFamily = lexendDecaFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = if (!enabled) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) else Color.Unspecified,
            )

            subtitle?.let {
                Text(
                    text = it,
                    fontFamily = lexendDecaFontFamily,
                    fontWeight = FontWeight.Normal,
                    color =
                        if (!enabled) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        },
                )
            }
        }
    }
}

@Composable
fun <T> DropDownPreference(
    title: String,
    subtitle: String,
    items: List<Pair<T, String>>,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var isOverflowMenuExpanded by remember { mutableStateOf(false) }
    Box {
        RegularPreference(
            modifier = modifier,
            title = title,
            subtitle = subtitle,
            onClick = { isOverflowMenuExpanded = true },
            enabled = enabled,
        )
        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape((12.dp)))) {
            DropdownMenu(
                isOverflowMenuExpanded,
                onDismissRequest = { isOverflowMenuExpanded = false },
                offset = DpOffset(16.dp, 0.dp),
            ) {
                items.forEach { item ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isOverflowMenuExpanded = false
                                    onItemSelected(item.first)
                                },
                    ) {
                        Text(
                            text = item.second,
                            fontFamily = lexendDecaFontFamily,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(all = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsCategory(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        modifier
            .padding(top = 24.dp, bottom = 8.dp),
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        fontFamily = lexendDecaFontFamily,
    )
}

@Composable
fun Separator(modifier: Modifier = Modifier) {
    Spacer(
        modifier =
            modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.LightGray)
                .height(0.25.dp)
                .fillMaxWidth(),
    )
}

@Preview
@Composable
fun RegularPreferencePreview() {
    RegularPreference(title = "Theme", subtitle = "Light", onClick = { })
}
