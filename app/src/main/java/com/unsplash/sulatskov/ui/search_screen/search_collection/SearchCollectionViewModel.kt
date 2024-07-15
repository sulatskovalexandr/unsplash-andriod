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


//    private val _searchCollectionList =
//        MutableStateFlow<Event<List<CollectionDto>>>(Event.loading())
//    val searchCollectionList: StateFlow<Event<List<CollectionDto>>> =
//        _searchCollectionList.asStateFlow()
//
//    private val _messageFlow = MutableStateFlow<Messages?>(null)
//    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()
//
//    private var param = SearchCollectionParam("", 1)
//
//    private var isLoading = false
//    private var isSuccess = false
//
//    override fun onViewCreated() {
//        if (param.page == 1) {
//            loadCollection()
//            _messageFlow.value = Messages.ShowShimmer
//        } else
//            _messageFlow.value = Messages.HideShimmer
//    }
//
//    private fun loadCollection() {
//        isLoading = true
//        viewModelScope.launch {
//            _searchCollectionList.value = Event.loading()
//            getSearchCollectionUseCase.invoke(param)
//                .onSuccess {
//                    isSuccess = true
//                    isLoading = it.size != 10
//                    _searchCollectionList.value = Event.success(it)
//                    _messageFlow.value = Messages.HideShimmer
//                    param = param.copy(page = param.page + 1)
//                }.onFailure {
//                    delay(1000)
//                    isLoading = false
//                    _messageFlow.value = Messages.HideShimmer
//                    _messageFlow.value = Messages.NetworkIsDisconnected
//                }
//        }
//    }
//
//    fun onLoadSearchCollection() {
//        if (!isLoading && isSuccess) {
//            loadCollection()
//        }
//    }

    fun setArgs(query: String) {
        if (param.query != query) {
            param = param.copy(query = query)
        }
    }
//
//    fun clearMessage() {
//        _messageFlow.value = null
//    }
}
