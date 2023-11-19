package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rob729.newsfeed.R
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.search.SearchSideEffects
import com.rob729.newsfeed.ui.components.LoadingView
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.vm.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = koinViewModel(),
    openCustomTab: (url: String) -> Unit
) {

    val searchState = viewModel.collectAsState().value

    val listState = rememberLazyListState()

    var active by rememberSaveable { mutableStateOf(false) }

    viewModel.collectSideEffect {
        when (it) {
            is SearchSideEffects.SearchQueryChanged -> {

            }

            is SearchSideEffects.SearchResultClicked -> {
                openCustomTab(it.selectedResultUrl)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column {

            Surface(tonalElevation = 4.dp, color = colorResource(R.color.status_bar)) {
                TextField(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .testTag("search_input_text_field"),
                    value = searchState.searchQuery,
                    textStyle = TextStyle(fontFamily = lexendDecaFontFamily),
                    onValueChange = {
                        viewModel.updateSearchQuery(it)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            })
                    },
                    trailingIcon = {
                        if (searchState.searchQuery.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear",
                                modifier = Modifier.clickable {
                                    viewModel.updateSearchQuery("")
                                })
                        }
                    },
                    placeholder = { Text("search here", fontFamily = lexendDecaFontFamily) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            }

            when (searchState.uiStatus) {
                UiStatus.Error -> {
//                    NoInternetView(viewModel::tryAgainClicked)
                }

                UiStatus.Loading -> {
                    LoadingView()
                }

                is UiStatus.Success -> {
                    LazyColumn(Modifier.testTag("search_result_news_list"), listState) {
                        items(searchState.uiStatus.news) { item ->
                            NewsFeedItem(newsArticleUiData = item) {
                                viewModel.newsFeedItemClicked(item)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}