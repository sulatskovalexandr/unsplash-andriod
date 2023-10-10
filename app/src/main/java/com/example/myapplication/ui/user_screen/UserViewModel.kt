package com.example.myapplication.ui.user_screen

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.use_case.user_usecase.GetUserUseCase
import com.example.myapplication.ui.base.BaseViewModel
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

    override fun onViewCreated() {
        loadUser(userName)
    }

    private fun loadUser(userName: String) {
        viewModelScope.launch {
            _user.value = getUser.execute(userName)

        }
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

}