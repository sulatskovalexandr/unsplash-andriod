package com.unsplash.sulatskov.ui.user_screen.user_collection

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.use_case.user_usecase.GetUserCollectionUseCase
import com.unsplash.sulatskov.domain.use_case.user_usecase.UserPhotoParam
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserCollectionViewModel @Inject constructor(
    private val getUserCollectionUseCase: GetUserCollectionUseCase
) : BaseViewModel() {

    private val _userCollectionListDtoDto =
        MutableStateFlow<Event<List<CollectionDto>>>(Event.loading())
    val userCollectionListDtoDto: StateFlow<Event<List<CollectionDto>>> =
        _userCollectionListDtoDto.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: StateFlow<Messages?> = _messageFlow.asStateFlow()

    private var param = UserPhotoParam("", 1)
    private var isLoading = false
    private var isSuccess = false

    /**
     * Вызывается на onViewCreated у UserCollectionFragment
     */
    override fun onViewCreated() {
        if (param.page == 1) {
            loadUserCollection(param)
            _messageFlow.value = Messages.ShowShimmer
        } else
            _messageFlow.value = Messages.HideShimmer
    }

    /**
     * Загружает список коллекций пользователя
     */
    private fun loadUserCollection(param: UserPhotoParam) {
        isLoading = true
        viewModelScope.launch {
            _userCollectionListDtoDto.value = Event.loading()
            getUserCollectionUseCase.invoke(param)
                .onSuccess {
                    isSuccess = true
                    isLoading = it.size != 10
                    _userCollectionListDtoDto.value = Event.success(it)
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

    /**
     * Загружает следующую страницу в списке коллекций пользователя
     */
    fun onLoadUserCollection() {
        if (!isLoading && isSuccess) {
            loadUserCollection(param)
            _messageFlow.value = Messages.HideShimmer
        }
    }

    /**
     * Загружает 1 страницу в списке коллекций пользователя при обновлении
     */
    fun onRefreshUserCollection() {
        loadUserCollection(param.copy(page = 1))
        _messageFlow.value = Messages.ShowShimmer
    }

    fun setArgs(userName: String) {
        param = param.copy(userName = userName)
    }
}
