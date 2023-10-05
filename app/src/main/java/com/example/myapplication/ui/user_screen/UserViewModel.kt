package com.example.myapplication.ui.user_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.use_case.user_usecase.GetUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUser: GetUserUseCase
) : ViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: Flow<User?> = _user.filterNotNull()

    private fun loadUser(userName: String) {
        viewModelScope.launch {
            val execute = getUser.execute(userName)
            _user.value = execute
        }
    }

    fun setUserName(userName: String) {
        loadUser(userName)
    }

}