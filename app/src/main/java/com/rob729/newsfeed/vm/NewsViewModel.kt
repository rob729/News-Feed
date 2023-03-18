package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.mapper.mapArticleDbDataToNewsArticleUiData
import com.rob729.newsfeed.model.state.NewsFeedSideEffect
import com.rob729.newsfeed.model.state.NewsFeedState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(),
    ContainerHost<NewsFeedState, NewsFeedSideEffect> {

    override val container: Container<NewsFeedState, NewsFeedSideEffect> = container(
        NewsFeedState()
    )

    init {
        intent {
            this.updateStateFromNewsResource(newsRepository.getNewsArticles(state.selectedNewsSource))
        }
    }

    private suspend fun SimpleSyntax<NewsFeedState, NewsFeedSideEffect>.updateStateFromNewsResource(
        newsResource: NewsResource
    ) {
        when (newsResource) {
            is NewsResource.Error -> {
                reduce {
                    state.copy(isLoading = false)
                }
            }
            NewsResource.Loading -> {
                reduce {
                    state.copy(isLoading = true)
                }
            }
            is NewsResource.Success<*> -> {
                (newsResource.data as? List<ArticleDbData>)?.let {
                    reduce {
                        state.copy(
                            isLoading = false,
                            news = it.mapNotNull(::mapArticleDbDataToNewsArticleUiData)
                        )
                    }
                }
            }
        }
    }

    fun newsSourceClicked(newsSource: String) = intent {
        postSideEffect(NewsFeedSideEffect.NewsSourceClicked(newsSource))
        if (state.selectedNewsSource != newsSource) {
            reduce {
                state.copy(selectedNewsSource = newsSource, isLoading = true, showNewsSourceBottomSheet = false)
            }
            this.updateStateFromNewsResource(newsRepository.getNewsArticles(newsSource))
        } else {
            reduce {
                state.copy(showNewsSourceBottomSheet = false)
            }
        }
    }

    fun newsFeedItemClicked(item: NewsArticleUiData) = intent {
        reduce {
            state.copy(selectedNewsUrl = item.url)
        }
        postSideEffect(NewsFeedSideEffect.NewsFeedItemClicked)
    }

    fun newsSourceFabClicked() = intent {
        reduce {
            state.copy(showNewsSourceBottomSheet = true)
        }
        postSideEffect(NewsFeedSideEffect.NewsSourceFabClicked)
    }

    fun scrollToTopClicked() = intent {
        postSideEffect(NewsFeedSideEffect.ScrollToTopClicked)
    }

}

