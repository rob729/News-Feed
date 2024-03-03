package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import com.rob729.newsfeed.model.mapper.mapBookmarkedNewsArticleToNewsArticleUiData
import com.rob729.newsfeed.model.state.bookmarkedArticles.BookmarkedArticleSideEffect
import com.rob729.newsfeed.model.state.bookmarkedArticles.BookmarkedArticlesState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class BookmarkedArticlesVM(private val newsRepository: NewsRepository) : ViewModel(),
    ContainerHost<BookmarkedArticlesState, BookmarkedArticleSideEffect> {

    override val container: Container<BookmarkedArticlesState, BookmarkedArticleSideEffect> =
        container(
            BookmarkedArticlesState()
        )

    init {
        intent {
            newsRepository.getBookmarkedNewsArticles().collectLatest { bookmarkedArticles ->
                reduce {
                    state.copy(
                        bookmarkedArticles =
                        bookmarkedArticles.map(::mapBookmarkedNewsArticleToNewsArticleUiData)
                    )
                }
            }
        }
    }

    fun newsFeedItemClicked(item: NewsArticleUiData) = intent {
        postSideEffect(BookmarkedArticleSideEffect.BookmarkedArticleClicked(item.url))
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
