package com.unsplash.sulatskov.ui.collection_screens.collection_details

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.domain.model.CollectionDetails
import com.unsplash.sulatskov.domain.model.CollectionPhotos
import com.unsplash.sulatskov.domain.use_case.collection_details.GetCollectionDetailsUseCase
import com.unsplash.sulatskov.domain.use_case.collection_details.GetCollectionPhotosUseCase
import com.unsplash.sulatskov.domain.use_case.collection_details.ParamCollectionPhotos
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectionDetailsViewModel @Inject constructor(
    private val getCollectionDetailsUseCase: GetCollectionDetailsUseCase,
    private val getCollectionPhotosUseCase: GetCollectionPhotosUseCase
) : BaseViewModel() {

    private val _collectionDetails: MutableStateFlow<CollectionDetails?> = MutableStateFlow(null)
    val collectionDetails: Flow<CollectionDetails> = _collectionDetails.filterNotNull()

    private val _collectionPhotos = MutableStateFlow<Event<List<CollectionPhotos>>>(Event.loading())
    val collectionPhotos: StateFlow<Event<List<CollectionPhotos>>> = _collectionPhotos.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private lateinit var collectionId: String

    private lateinit var param: ParamCollectionPhotos
    private var isLoading = false
    private var isSuccess = false

    override fun onViewCreated() {
        super.onViewCreated()
        loadCollectionDetails(collectionId)
        if (param.page == 1) {
            loadCollectionPhotos(param)
            _messageFlow.value = Messages.ShowShimmer
        } else {
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Получение информаании о коллекции
     */
    private fun loadCollectionDetails(collectionId: String) {
        viewModelScope.launch {
            getCollectionDetailsUseCase.invoke(collectionId)
                .onSuccess {
                    _collectionDetails.value = it
                }
        }
    }

    /**
     * Получение списка фото коллекции
     */
    private fun loadCollectionPhotos(param: ParamCollectionPhotos) {
        isLoading = true
        viewModelScope.launch {
            _collectionPhotos.value = Event.loading()
            getCollectionPhotosUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    _collectionPhotos.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@CollectionDetailsViewModel.param = param.copy(
                        page = param.page + 1
                    )
                }.onFailure {
                    delay(1000)
                    _messageFlow.value = Messages.NetworkIsDisconnected
                    _messageFlow.value = Messages.HideShimmer
                }
            isLoading = false
        }
    }

    /**
     * Загрузка следующей страницы param+1
     */

    fun onLoadCollectionPhotos() {
        if (!isLoading && isSuccess) {
            loadCollectionPhotos(param)
        }
    }

    /**
     * Получение ID коллекции
     */

    fun setCollectionId(collectionId: String) {
        this.collectionId = collectionId
        param = ParamCollectionPhotos(id = collectionId, page = 1)
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}