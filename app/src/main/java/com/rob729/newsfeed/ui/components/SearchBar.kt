package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun SearchBar(
    searchQuery: String,
    updateSearchQuery: (String) -> Unit,
    clearEditText: () -> Unit,
    onLeadingIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        modifier =
            modifier
                .padding(12.dp)
                .fillMaxWidth()
                .testTag("search_input_text_field"),
        value = searchQuery,
        textStyle = TextStyle(fontFamily = lexendDecaFontFamily),
        onValueChange = {
            updateSearchQuery(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier =
                    Modifier.clickable {
                        onLeadingIconClick()
                    },
            )
        },
        trailingIcon = {
            if (searchQuery.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear",
                    modifier =
                        Modifier.clickable {
                            clearEditText()
                        },
                )
            }
        },
        placeholder = { Text("search here", fontFamily = lexendDecaFontFamily) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        shape = RoundedCornerShape(12.dp),
        colors =
            TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
    )
}
