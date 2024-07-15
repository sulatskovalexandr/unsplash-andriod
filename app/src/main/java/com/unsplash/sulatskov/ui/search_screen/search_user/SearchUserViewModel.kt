package com.unsplash.sulatskov.ui.search_screen.search_user

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.unsplash.sulatskov.data.paging.SearchUserPagingSource
import com.unsplash.sulatskov.domain.repository.SearchRepository
import com.unsplash.sulatskov.domain.use_case.search_usecase.SearchCollectionParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    BaseViewModel() {

    private var param = SearchCollectionParam("", 1)

    val searchUsersList = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = true,
            initialLoadSize = 10
        )
    ) {
        SearchUserPagingSource(
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