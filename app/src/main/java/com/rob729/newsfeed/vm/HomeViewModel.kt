package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.mapper.mapArticleDbDataToNewsArticleUiData
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.home.HomeFeedSideEffect
import com.rob729.newsfeed.model.state.home.HomeFeedState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.repository.NewsRepository
import com.rob729.newsfeed.repository.PreferenceRepository
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel(
    private val newsRepository: NewsRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel(),
    ContainerHost<HomeFeedState, HomeFeedSideEffect> {

    override val container: Container<HomeFeedState, HomeFeedSideEffect> = container(
        HomeFeedState()
    )

    init {
        intent {
            newsRepository.getNewsArticles(state.selectedNewsSource).collectLatest {
                this.updateStateFromNewsResource(it)
            }
            preferenceRepository.getNewsSources().collectLatest {
                this.reduce {
                    state.copy(newsSources = it)
                }
            }
        }

        intent {
            preferenceRepository.shouldOpenLinksUsingInAppBrowser().collectLatest {
                this.reduce {
                    state.copy(shouldOpenLinksUsingInAppBrowser = it)
                }
            }
        }
    }

    private suspend fun SimpleSyntax<HomeFeedState, HomeFeedSideEffect>.updateStateFromNewsResource(
        newsResource: NewsResource
    ) {
        when (newsResource) {
            is NewsResource.Error -> {
                reduce {
                    state.copy(uiStatus = UiStatus.Error)
                }
            }

            NewsResource.Loading -> {
                reduce {
                    state.copy(uiStatus = UiStatus.Loading)
                }
            }

            is NewsResource.Success<*> -> {
                (newsResource.data as? List<ArticleDbData>)?.let {
                    reduce {
                        state.copy(
                            uiStatus = UiStatus.Success(it.mapNotNull(::mapArticleDbDataToNewsArticleUiData))
                        )
                    }
                }
            }
        }
    }

    fun newsSourceClicked(newsSource: String) = intent {
        postSideEffect(HomeFeedSideEffect.NewsSourceClicked(newsSource))
        if (state.selectedNewsSource != newsSource) {
            reduce {
                state.copy(selectedNewsSource = newsSource, showNewsSourceBottomSheet = false)
            }
            newsRepository.getNewsArticles(newsSource).collectLatest {
                this.updateStateFromNewsResource(it)
            }
        } else {
            reduce {
                state.copy(showNewsSourceBottomSheet = false)
            }
        }
    }

    fun newsFeedItemClicked(item: NewsArticleUiData) = intent {
        postSideEffect(HomeFeedSideEffect.FeedItemClicked(item.url))
    }

    fun newsSourceFabClicked() = intent {
        reduce {
            state.copy(showNewsSourceBottomSheet = true)
        }
        postSideEffect(HomeFeedSideEffect.NewsSourceFabClicked)
    }

    fun scrollToTopClicked() = intent {
        postSideEffect(HomeFeedSideEffect.ScrollToTopClicked)
    }

    fun tryAgainClicked() = intent {
        newsRepository.getNewsArticles(state.selectedNewsSource).collectLatest {
            this.updateStateFromNewsResource(it)
        }
    }

    fun newsFeedItemBookmarkClicked(newsArticleUiData: NewsArticleUiData, isBookmarked: Boolean) =
        intent {
            if (isBookmarked) {
                newsRepository.addBookmarkedNewsArticle(newsArticleUiData)
            } else {
                newsRepository.removeBookmarkedNewsArticle(newsArticleUiData.url)
            }
        }
}

