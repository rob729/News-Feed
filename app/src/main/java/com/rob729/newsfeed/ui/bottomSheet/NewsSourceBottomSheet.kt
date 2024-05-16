package com.rob729.newsfeed.ui.bottomSheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.rob729.newsfeed.AppPreferences.NewsSource
import com.rob729.newsfeed.ui.components.NewsSourceBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewsSourceBottomSheet(
    newsSources: List<NewsSource>,
    bottomSheetState: SheetState,
    isBottomSheetVisible: Boolean,
    onNewsSourceClicked: (String) -> Unit,
    selectedNewsSource: String,
    onDismissRequest: () -> Unit
) {

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            sheetState = bottomSheetState,
            scrimColor = Color.Black.copy(alpha = 0.6f),
            onDismissRequest = onDismissRequest
        ) {
            NewsSourceBottomSheetContent(
                newsSources,
                onNewsSourceClicked,
                selectedNewsSource
            )
        }
    }
}
