package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    onCheckedChange(checked.not())
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        ) {
            Text(
                text = title,
                fontFamily = lexendDecaFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = if (!enabled) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) else Color.Unspecified,
            )

            subtitle?.let {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = it,
                    fontFamily = lexendDecaFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color =
                        if (!enabled) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        } else {
                            Color.Unspecified
                        },
                )
            }
        }
        Box(
            modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Switch(
                enabled = enabled,
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors =
                    SwitchDefaults.colors(
                        uncheckedThumbColor = Color.LightGray,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwitchPreferencePreview() {
    SwitchPreference(
        title = "Setting",
        subtitle = "clicking on the toggle will toggle it djndkwn nwikndjn siw",
        checked = true,
        enabled = true,
        onCheckedChange = {},
    )
}
