package com.unsplash.sulatskov.ui.search_screen.search_collection

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.unsplash.sulatskov.data.paging.SearchCollectionPagingSource
import com.unsplash.sulatskov.domain.repository.SearchRepository
import com.unsplash.sulatskov.domain.use_case.search_usecase.SearchCollectionParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import javax.inject.Inject

class SearchCollectionViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    BaseViewModel() {
    private var param = SearchCollectionParam("", 1)

    val searchCollectionsList = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = true,
            initialLoadSize = 10
        )
    ) {
        SearchCollectionPagingSource(
            searchRepository = searchRepository,
            query = param.query
        )
    }.flow.cachedIn(viewModelScope)

    fun setArgs(query: String) {
        if (param.query != query) {
            param = param.copy(query = query)
        }
    }
}