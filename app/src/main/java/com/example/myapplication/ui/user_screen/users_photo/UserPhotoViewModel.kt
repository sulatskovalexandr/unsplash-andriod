package com.example.myapplication.ui.user_screen.users_photo

import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.domain.model.UserPhoto
import com.example.myapplication.domain.use_case.user_usecase.GetUserPhotoUseCase
import com.example.myapplication.domain.use_case.user_usecase.UserPhotoParam
import com.example.myapplication.ui.base.BaseViewModel
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

    override fun onViewCreated() {
        loadUserPhoto(param)
    }

    private fun loadUserPhoto(param: UserPhotoParam) {
        isLoading = true
        viewModelScope.launch {
            _userPhotoList.value = Event.loading()
            getUserPhotoUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    _userPhotoList.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@UserPhotoViewModel.param = param.copy(page = param.page + 1)
                    isLoading = it.size == 10
                }.onFailure {
                    _messageFlow.value = Messages.ShowShimmer
                }
            isLoading = false
        }
    }

    fun onLoadUserPhotos() {
        if (!isLoading && isSuccess) {
            loadUserPhoto(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    fun onRefreshUserPhoto() {
        loadUserPhoto(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
    }

    fun setArgs(userName: String) {
        param = param.copy(userName = userName)
    }
}