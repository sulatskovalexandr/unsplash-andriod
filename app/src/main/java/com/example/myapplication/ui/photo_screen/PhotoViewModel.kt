package com.example.myapplication.ui.photo_screen

import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.use_case.photo_usecase.GetDataBasePhotoUseCase
import com.example.myapplication.domain.use_case.photo_usecase.GetPhotoUseCase
import com.example.myapplication.domain.use_case.photo_usecase.ListPhotoParam
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getDataBasePhotoUseCase: GetDataBasePhotoUseCase,
//    private val networkChecker: NetworkChecker
) : BaseViewModel() {

    private val _photoList = MutableStateFlow<Event<List<Photo>>>(Event.loading())
    val photoList: StateFlow<Event<List<Photo>>> = _photoList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var param = ListPhotoParam(1, GetPhotoUseCase.Companion.Order.LATEST)
    private var isLoading = false
    private var isSuccess = false

    override fun onViewCreated() {
        loadPhoto(param = param)
        if (param.page == 1) {
            _messageFlow.value = Messages.ShowShimmer
        } else {
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загрузить фотографии
     */
    private fun loadPhoto(param: ListPhotoParam) {
        isLoading = true
        viewModelScope.launch {
            _photoList.value = Event.loading()
            getPhotoUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    _photoList.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@PhotoViewModel.param = param.copy(page = param.page + 1)
                }.onFailure {
                    _messageFlow.value = Messages.NetworkIsDisconnected
                    getDataBasePhotoUseCase.invoke(1)
                        .onSuccess {
                            _photoList.value = Event.success(it)
                            _messageFlow.value = Messages.HideShimmer
                        }.onFailure {
                            _messageFlow.value = Messages.NetworkIsDisconnected
                        }
                }
            isLoading = false
        }
    }

    fun onLoadPhotos() {
        if (!isLoading && isSuccess) {
            loadPhoto(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    fun onRefreshPhotos() {
        loadPhoto(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
        _messageFlow.value = Messages.NetworkIsDisconnected
    }

    fun loadListOldestPhoto() {
        loadPhoto(
            param = param.copy(
                page = param.page,
                orderBy = GetPhotoUseCase.Companion.Order.OLDEST
            )
        )
    }

    fun loadListPopularPhoto() {
        loadPhoto(
            param = param.copy(
                page = param.page,
                orderBy = GetPhotoUseCase.Companion.Order.POPULAR
            )
        )
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}
