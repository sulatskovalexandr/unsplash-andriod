package com.unsplash.sulatskov.ui.search_screen.search_photo

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.unsplash.sulatskov.data.paging.SearchPhotoPagingSource
import com.unsplash.sulatskov.domain.repository.SearchRepository
import com.unsplash.sulatskov.domain.use_case.search_usecase.SearchPhotoParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import javax.inject.Inject

class SearchPhotoViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    BaseViewModel() {

    private var param =
        SearchPhotoParam(
            "",
            1,
            OrderSearchPhoto.LATEST,
            ContentFilterSearchPhoto.LOW,
            ColorSearchPhoto.BLACK,
            OrientationSearchPhoto.LANDSCAPE
        )
    val searchPhotosList = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = true,
            initialLoadSize = 10
        )
    ) {
        SearchPhotoPagingSource(
            searchRepository = searchRepository,
            query = param.query,
            orderBy = param.orderBy.value,
            contentFilter = param.contentFilter.value,
            color = param.color?.value,
            orientation = param.orientation?.value,
        )
    }.flow.cachedIn(viewModelScope)

    //    private val _searchPhotoList = MutableStateFlow<Event<List<PhotoDto>>>(Event.loading())
//    val searchPhotosList: StateFlow<Event<List<PhotoDto>>> = _searchPhotoList.asStateFlow()
//
//    private val _messageFlow = MutableStateFlow<Messages?>(null)
//    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()


    //    private var isLoading = false
//    private var isSuccess = false
//
////    val pager = Pager(
////        PagingConfig(10),
////        param
////    ) {
////        SearchPhotoPagingSource()
////    }
//
//    override fun onViewCreated() {
//        if (param.page == 1) {
////            pager.flow
//            onLoadSearchPhotos()
//            _messageFlow.value = Messages.ShowShimmer
//        } else
//            _messageFlow.value = Messages.HideShimmer
//    }
//
//    private fun loadSearchPhoto() {
//        isLoading = true
//        viewModelScope.launch {
//            _searchPhotoList.value = Event.loading()
//            getSearchPhotoUseCase.invoke(param)
//                .onSuccess {
//                    isSuccess = true
//                    isLoading = it.size != 10
//                    _searchPhotoList.value = Event.success(it)
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
//    fun onLoadSearchPhotos() {
//        if (!isLoading && isSuccess) {
//            loadSearchPhoto()
//            _messageFlow.value = Messages.HideShimmer
//        }
//    }
//
    fun setArgs(query: String) {
        if (param.query != query) {
            param = param.copy(query = query)
//            _messageFlow.value = Messages.ShowShimmer
//            loadSearchPhoto()
        }
    }
//
//    fun clearMessage() {
//        _messageFlow.value = null
//    }
}