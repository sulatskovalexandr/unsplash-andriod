package com.example.myapplication.ui.login_screen

import androidx.lifecycle.viewModelScope
import com.example.myapplication.Event
import com.example.myapplication.data.repository.AccessTokenProvider
import com.example.myapplication.domain.use_case.login_usecase.GetMeUseCase
import com.example.myapplication.domain.use_case.login_usecase.LoginUseCase
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val accessTokenProvider: AccessTokenProvider
) : BaseViewModel() {

//    private var code: String = ""
//    override fun onViewCreated() {
//        login(code)
//    }

    private val _token = MutableStateFlow<Event<String>>(Event.loading())
    val token: StateFlow<Event<String>> = _token.asStateFlow()

    fun login(code: String) {
        viewModelScope.launch {
            loginUseCase.invoke(code)
                .onSuccess {
                    _token.value = Event.success(it)
                    getMeUseCase.execute()
                }.onFailure {
                    _token.value = Event.error()
                }
        }
    }

//    fun setCode(code: String) {
//        this.code = code
//    }

    fun saveToken(token: String) {
        accessTokenProvider.accessToken = token
    }

}