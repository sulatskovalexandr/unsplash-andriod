package com.example.myapplication.ui.user_screen.user_collection

import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.common.Messages
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.use_case.user_usecase.GetUserCollectionUseCase
import com.example.myapplication.domain.use_case.user_usecase.UserPhotoParam
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserCollectionViewModel @Inject constructor(
    private val getUserCollectionUseCase: GetUserCollectionUseCase
) : BaseViewModel() {

    private val _userCollectionList =
        MutableStateFlow<Event<List<Collection>>>(Event.loading())
    val userCollectionList: StateFlow<Event<List<Collection>>> =
        _userCollectionList.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var param = UserPhotoParam("", 1)
    private var isLoading = false
    private var isSuccess = false

    override fun onViewCreated() {
        if (param.page == 1) {
            loadUserCollection(param)
            _messageFlow.value = Messages.ShowShimmer
        } else
            _messageFlow.value = Messages.HideShimmer
    }

    private fun loadUserCollection(param: UserPhotoParam) {
        isLoading = true
        viewModelScope.launch {
            _userCollectionList.value = Event.loading()
            getUserCollectionUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    isLoading = it.size != 10
                    _userCollectionList.value = Event.success(it)
                    _messageFlow.value = Messages.HideShimmer
                    this@UserCollectionViewModel.param = param.copy(page = param.page + 1)
                }.onFailure {
                    delay(1000)
                    isLoading = false
                    _messageFlow.value = Messages.HideShimmer
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
        }
    }

    fun onLoadUserCollection() {
        if (!isLoading && isSuccess) {
            loadUserCollection(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    fun onRefreshUserCollection() {
        loadUserCollection(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
    }

    fun setArgs(userName: String) {
        param = param.copy(userName = userName)
    }
}
