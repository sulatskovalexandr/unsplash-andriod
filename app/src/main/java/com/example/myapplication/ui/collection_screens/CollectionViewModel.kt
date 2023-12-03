package com.example.myapplication.ui.collection_screens

import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.use_case.collection_usecase.GetCollectionUseCase
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase,
//    private val networkChecker: NetworkChecker
) : BaseViewModel() {

    private val _collectionList = MutableStateFlow<Event<List<Collection>>>(Event.loading())
    val collectionList: StateFlow<Event<List<Collection>>> = _collectionList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var page = 1
    private var isLoading = false
    private var isSuccess = false

    override fun onViewCreated() {
        if (page == 1) {
            loadCollection(page)
            _messageFlow.value = Messages.ShowShimmer
        } else {
            _messageFlow.value = Messages.HideShimmer
        }
    }

    private fun loadCollection(page: Int) {
        isLoading = true
        viewModelScope.launch {
//            delay(2000)
            _collectionList.value = Event.loading()

            getCollectionUseCase.invoke(page)
                .onSuccess {
                    isSuccess = true
                    _collectionList.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@CollectionViewModel.page = page + 1

                }.onFailure {
                    _messageFlow.value = Messages.ShowShimmer
                    _collectionList.value = Event.error(0)
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
            isLoading = false
        }
    }

    fun onLoadCollection() {
        if (!isLoading && isSuccess) {
            loadCollection(page)
        }
    }

    fun onRefreshCollection() {
        loadCollection(1)
        _messageFlow.value = Messages.ShowShimmer
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}