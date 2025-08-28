package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import com.rob729.newsfeed.model.mapper.mapBookmarkedNewsArticleToNewsArticleUiData
import com.rob729.newsfeed.model.state.bookmarkedArticles.BookmarkedArticleSideEffect
import com.rob729.newsfeed.model.state.bookmarkedArticles.BookmarkedArticlesState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.repository.NewsRepository
import com.rob729.newsfeed.repository.PreferenceRepository
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class BookmarkedArticlesVM(
    private val newsRepository: NewsRepository,
    private val preferenceRepository: PreferenceRepository,
) : ViewModel(),
    ContainerHost<BookmarkedArticlesState, BookmarkedArticleSideEffect> {
    override val container: Container<BookmarkedArticlesState, BookmarkedArticleSideEffect> =
        container(
            BookmarkedArticlesState(),
        )

    init {
        intent {
            newsRepository.getBookmarkedNewsArticles().collectLatest { bookmarkedArticles ->
                reduce {
                    state.copy(
                        bookmarkedArticles =
                            bookmarkedArticles.map(::mapBookmarkedNewsArticleToNewsArticleUiData),
                    )
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

    fun newsFeedItemClicked(item: NewsArticleUiData) =
        intent {
            postSideEffect(BookmarkedArticleSideEffect.BookmarkedArticleClicked(item.url))
        }

    fun newsFeedItemBookmarkClicked(
        newsArticleUiData: NewsArticleUiData,
        isBookmarked: Boolean,
    ) = intent {
        if (isBookmarked) {
            newsRepository.addBookmarkedNewsArticle(newsArticleUiData)
        } else {
            newsRepository.removeBookmarkedNewsArticle(newsArticleUiData.url)
        }
    }
}
