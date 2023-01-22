package com.rob729.newsfeed.model.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}