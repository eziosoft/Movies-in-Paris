package com.eziosoft.moviesInParis.domain.paginator

interface Paginator<Key, Page> {
    suspend fun loadNextPage()
    fun reset()
}
