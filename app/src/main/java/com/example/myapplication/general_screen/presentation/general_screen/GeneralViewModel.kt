package com.example.myapplication.general_screen.presentation.general_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.NetworkChecker
import com.example.myapplication.general_screen.domain.general_usecases.GetDataBasePhotoUseCase
import com.example.myapplication.general_screen.domain.general_usecases.GetPhotoUseCase
import com.example.myapplication.general_screen.domain.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class GeneralViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val gerDataBasePhotoUseCase: GetDataBasePhotoUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {
    //    private val _photoList: MutableStateFlow<List<Photos>?> = MutableStateFlow(null)
    private val _photoList = MutableStateFlow<Event<List<Photo>>>(Event.loading())

    //    val photoList: StateFlow<List<Photos>?> = _photoList.asStateFlow()
    val photoList: StateFlow<Event<List<Photo>>> = _photoList.asStateFlow()


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
        viewModelScope.launch {
            _photoList.value = Event.loading()
            if (networkChecker.isNetworkConnected()) {

                getPhotoUseCase.invoke(page)
                    .onSuccess {
                        isSuccess = true
                        _photoList.value = Event.success(it)
                    }.onFailure {
                        gerDataBasePhotoUseCase.invoke(page)
                            .onSuccess {
                                _photoList.value = Event.success(it)
                            }.onFailure {
                                _photoList.value = Event.error()
                            }
                    }
            } else {
                gerDataBasePhotoUseCase.invoke(page)
                    .onSuccess {
                        _photoList.value = Event.success(it)
                    }.onFailure {
                        _photoList.value = Event.error()
                    }
            }
//            _photoList.value = getPhotoUseCase.execute(page)
        }
        isLoading = false
        currentPage = page + 1
    }

    fun onRefreshPhotos() {
        currentPage = 1
        loadPhoto(currentPage)
    }

    fun onLoadPhotos() {
        if (!isLoading && isSuccess) {
            isLoading = true
            loadPhoto(currentPage)
        }
    }
}
