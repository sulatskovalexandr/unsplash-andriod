package com.example.myapplication.ui.photo_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.common.NetworkChecker
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.use_case.photo_usecase.GetDataBasePhotoUseCase
import com.example.myapplication.domain.use_case.photo_usecase.GetPhotoUseCase
import com.example.myapplication.domain.use_case.photo_usecase.ListPhotoParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getDataBasePhotoUseCase: GetDataBasePhotoUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _photoList = MutableStateFlow<Event<List<Photo>>>(Event.loading())
    val photoList: StateFlow<Event<List<Photo>>> = _photoList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()


    private var param = ListPhotoParam(1, GetPhotoUseCase.Companion.Order.LATEST)
    private var isLoading = false
    private var isSuccess = false

    init {
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
            if (networkChecker.isNetworkConnected()) {
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
                            }.onFailure {
                                _messageFlow.value = Messages.NetworkIsDisconnected
                            }
                    }
            } else {
                _messageFlow.value = Messages.NetworkIsDisconnected
                getDataBasePhotoUseCase.invoke(1)
                    .onSuccess {
                        _photoList.value = Event.success(it)
                        _messageFlow.value = Messages.HideShimmer

                    }.onFailure {
                        _messageFlow.value = Messages.NetworkIsDisconnected
                    }
            }
//            currentPage += 1
            isLoading = false
//            _photoList.value = getPhotoUseCase.execute(page)
        }
    }

    fun onLoadPhotos() {
        if (networkChecker.isNetworkConnected()) {
            if (!isLoading && isSuccess) {
                loadPhoto(param)
            } else {
                isLoading = false
                loadPhoto(param.copy(page = 1))
            }
        }


    }

    fun onRefreshPhotos() {
        if (networkChecker.isNetworkConnected()) {
            loadPhoto(param.copy(page = 1))
            _messageFlow.value = Messages.ShowShimmer
        } else {
            loadPhoto(param.copy(page = 1))
            _messageFlow.value = Messages.ShowShimmer
            _messageFlow.value = Messages.NetworkIsDisconnected
        }
    }

    fun loadListOldestPhoto() {
        loadPhoto(param = param.copy(page = 1, orderBy = GetPhotoUseCase.Companion.Order.OLDEST))
    }

    fun loadListPopularPhoto() {
        loadPhoto(param = param.copy(page = 1, orderBy = GetPhotoUseCase.Companion.Order.POPULAR))
    }


    fun clearMessage() {
        _messageFlow.value = null
    }
}
