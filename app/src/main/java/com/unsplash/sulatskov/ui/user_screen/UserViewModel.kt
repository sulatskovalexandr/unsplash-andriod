package com.unsplash.sulatskov.ui.user_screen

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.use_case.user_usecase.GetUserUseCase
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUser: GetUserUseCase
) : BaseViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: Flow<User?> = _user.filterNotNull()

    private lateinit var userName: String

    /**
     * Вызывается на onViewCreated у UserFragment
     */
    override fun onViewCreated() {
        loadUser(userName)
    }

    /**
     * Загружает данные о пользователе
     */
    private fun loadUser(userName: String) {
        viewModelScope.launch {
            getUser.invoke(userName)
                .onSuccess {
                    _user.value = it
                }
        }
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }
}