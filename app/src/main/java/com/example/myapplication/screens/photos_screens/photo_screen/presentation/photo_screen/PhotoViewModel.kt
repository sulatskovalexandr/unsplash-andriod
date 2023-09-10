package com.example.myapplication.screens.photos_screens.photo_screen.presentation.photo_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.common.NetworkChecker
import com.example.myapplication.screens.photos_screens.photo_screen.domain.general_usecases.GetDataBasePhotoUseCase
import com.example.myapplication.screens.photos_screens.photo_screen.domain.general_usecases.GetPhotoUseCase
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val gerDataBasePhotoUseCase: GetDataBasePhotoUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _photoList = MutableStateFlow<Event<List<Photo>>>(Event.loading())
    val photoList: StateFlow<Event<List<Photo>>> = _photoList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var currentPage = 1
    private var isLoading = false
    private var isSuccess = false

    init {
        loadPhoto(currentPage)
    }

    /**
     * Загрузить фотографии
     */
    private fun loadPhoto(page: Int) {
        isLoading = true
        viewModelScope.launch {
            _photoList.value = Event.loading()
            if (networkChecker.isNetworkConnected()) {
                getPhotoUseCase.invoke(page)
                    .onSuccess {
                        isSuccess = true
                        _photoList.value = Event.success(it)
                    }.onFailure {
                        _messageFlow.value = Messages.NetworkIsDisconnected
                        gerDataBasePhotoUseCase.invoke(1)
                            .onSuccess {
                                _photoList.value = Event.success(it)
                            }.onFailure {
                                _messageFlow.value = Messages.NetworkIsDisconnected
                            }
                    }
            } else {
                _messageFlow.value = Messages.NetworkIsDisconnected
                gerDataBasePhotoUseCase.invoke(1)
                    .onSuccess {
                        _photoList.value = Event.success(it)
                    }.onFailure {
                        _messageFlow.value = Messages.NetworkIsDisconnected
                    }
            }
            currentPage = page + 1
            isLoading = false
//            _photoList.value = getPhotoUseCase.execute(page)
        }
    }

    fun onRefreshPhotos() {
        if (networkChecker.isNetworkConnected()) {
            currentPage = 1
            loadPhoto(currentPage)
        } else {
            currentPage = 1
            loadPhoto(currentPage)
            _messageFlow.value = Messages.NetworkIsDisconnected
        }
    }

    fun onLoadPhotos() {
        if (!isLoading && isSuccess) {
            loadPhoto(currentPage)
        }
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}
