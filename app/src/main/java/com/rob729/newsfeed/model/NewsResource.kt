package com.rob729.newsfeed.model

sealed class NewsResource {
    data class Success<T>(val data: T) : NewsResource()
    object Loading : NewsResource()
    data class Error(val message: String) : NewsResource()
}