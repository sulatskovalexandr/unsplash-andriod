package com.unsplash.sulatskov.ui.photo_screen

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.use_case.photo_usecase.GetDataBasePhotoUseCase
import com.unsplash.sulatskov.domain.use_case.photo_usecase.GetPhotoUseCase
import com.unsplash.sulatskov.domain.use_case.photo_usecase.ListPhotoParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhotoViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getDataBasePhotoUseCase: GetDataBasePhotoUseCase,
) : BaseViewModel() {

    private val _photoList = MutableStateFlow<Event<List<Photo>>>(Event.loading())
    val photoList: StateFlow<Event<List<Photo>>> = _photoList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var param = ListPhotoParam(1, OrderListPhoto.LATEST)
    private var isLoading = false
    private var isSuccess = false

    /**
     * Вызывается на onViewCreated у PhotoFragment
     */
    override fun onViewCreated() {
        loadPhoto(param = param)
        if (param.page == 1) {
            _messageFlow.value = Messages.ShowShimmer
        } else {
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загружает список фото
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
                    getDataBasePhotoUseCase.invoke(param)
                        .onSuccess {
                            _photoList.value = Event.success(it)
                            _messageFlow.value = Messages.HideShimmer
                            isLoading = false
                        }.onFailure {
                            _messageFlow.value = Messages.NetworkIsDisconnected
                        }
                }
            isLoading = false
        }
    }
    /**
     * Загружает следующую страницу в списке фото
     */
    fun onLoadPhotos() {
        if (!isLoading && isSuccess) {
            loadPhoto(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загружает 1 страницу в списке фото
     */
    fun onRefreshPhotos() {
        loadPhoto(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
    }

    /**
     * Загружает список фото оттсортированных по дате добавления (старые)
     */
    fun loadListOldestPhoto() {
        loadPhoto(
            param = param.copy(
                page = param.page,
                orderBy = OrderListPhoto.OLDEST
            )
        )
    }

    /**
     * Загружает список фото оттсортированных по популярности
     */
    fun loadListPopularPhoto() {
        loadPhoto(
            param = param.copy(
                page = param.page,
                orderBy = OrderListPhoto.POPULAR
            )
        )
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}
