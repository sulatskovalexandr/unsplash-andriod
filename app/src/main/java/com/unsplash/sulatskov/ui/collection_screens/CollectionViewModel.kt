package com.unsplash.sulatskov.ui.collection_screens

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.use_case.collection_usecase.GetCollectionUseCase
import com.unsplash.sulatskov.domain.use_case.collection_usecase.GetDataBaseCollectionUseCase
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase,
    private val getDataBaseCollectionUseCase: GetDataBaseCollectionUseCase
) : BaseViewModel() {

    private val _collectionList = MutableStateFlow<Event<List<Collection>>>(Event.loading())
    val collectionList: StateFlow<Event<List<Collection>>> = _collectionList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var page = 1
    private var isLoading = false
    private var isSuccess = false

    /**
     * Вызывается на onViewCreated у CollectionFragment
     */
    override fun onViewCreated() {
        if (page == 1) {
            loadCollection(page)
            _messageFlow.value = Messages.ShowShimmer
        } else {
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загружает список коллекций
     */
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
                    _messageFlow.value = Messages.NetworkIsDisconnected
                    getDataBaseCollectionUseCase.invoke(page)
                        .onSuccess {
                            _collectionList.value = Event.success(it)
                            _messageFlow.value = Messages.HideShimmer
                            isLoading = false
                        }.onFailure {
                            _messageFlow.value = Messages.NetworkIsDisconnected
                            _messageFlow.value = Messages.HideShimmer
                        }
                }
            isLoading = false
        }
    }

    /**
     * Загружает следующую страницу в списке коллекций
     */
    fun onLoadCollection() {
        if (!isLoading && isSuccess) {
            loadCollection(page)
        }
    }

    /**
     * Загружает 1 страницу в списке коллекций при обновлении
     */
    fun onRefreshCollection() {
        loadCollection(1)
        _messageFlow.value = Messages.ShowShimmer
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}