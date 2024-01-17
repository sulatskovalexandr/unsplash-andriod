package com.unsplash.sulatskov.ui.user_screen.users_photo

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.domain.model.UserPhoto
import com.unsplash.sulatskov.domain.use_case.user_usecase.GetUserPhotoUseCase
import com.unsplash.sulatskov.domain.use_case.user_usecase.UserPhotoParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserPhotoViewModel @Inject constructor(
    private val getUserPhotoUseCase: GetUserPhotoUseCase
) : BaseViewModel() {

    private val _userPhotoList =
        MutableStateFlow<Event<List<UserPhoto>>>(Event.loading())
    val userPhotoList: StateFlow<Event<List<UserPhoto>>> = _userPhotoList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var param = UserPhotoParam("", 1)
    private var isLoading = false
    private var isSuccess = false

    /**
     * Вызывается на onViewCreated у UserPhotoFragment
     */
    override fun onViewCreated() {
        if (param.page == 1) {
            loadUserPhoto(param)
            _messageFlow.value = Messages.ShowShimmer
        } else
            _messageFlow.value = Messages.HideShimmer
    }

    /**
     * Загружает список фото пользователя
     */
    private fun loadUserPhoto(param: UserPhotoParam) {
        isLoading = true
        viewModelScope.launch {
            _userPhotoList.value = Event.loading()
            getUserPhotoUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    isLoading = it.size != 10
                    _userPhotoList.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@UserPhotoViewModel.param = param.copy(page = param.page + 1)
                }.onFailure {
                    delay(1000)
                    isLoading = false
                    _messageFlow.value = Messages.HideShimmer
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
        }
    }

    /**
     * Загружает следующую страницу в списке фото пользователя
     */
    fun onLoadUserPhotos() {
        if (!isLoading && isSuccess) {
            loadUserPhoto(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загружает 1 страницу в списке фото пользователя при обновлении
     */
    fun onRefreshUserPhoto() {
        loadUserPhoto(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
    }

    fun setArgs(userName: String) {
        param = param.copy(userName = userName)
    }
}