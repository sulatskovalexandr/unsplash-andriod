package com.unsplash.sulatskov.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import com.unsplash.sulatskov.domain.repository.SearchRepository

class SearchPhotoPagingSource(
    private val searchRepository: SearchRepository,
    private val query: String,
    private val orderBy: String,
    private val contentFilter: String,
    private val color: String?,
    private val orientation: String?
) : PagingSource<Int, PhotoDto>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDto> {
        return try {
            val page = params.key ?: 1
            val perPage = 10
            val data = searchRepository.getSearchPhotos(
                 query = query,
                page = page,
                perPage = perPage,
                orderBy = orderBy,
                contentFilter = contentFilter,
                color = color,
                orientation = orientation
            )
            val nextKey = if (data.size < perPage) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}