package com.unsplash.sulatskov.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUserPagingSource @Inject constructor(
    private val searchRepository: SearchRepository,
    private val query: String
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val perPage = 10
            val data = searchRepository.getSearchUsers(
                query = query,
                page = page,
                perPage = perPage)
            val nextKey = if (data.size < perPage) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}