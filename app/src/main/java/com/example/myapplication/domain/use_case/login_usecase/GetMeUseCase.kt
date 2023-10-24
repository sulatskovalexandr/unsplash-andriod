package com.example.myapplication.domain.use_case.login_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.Me
import com.example.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetMeUseCase @Inject constructor(private val loginRepository: LoginRepository) :
    UseCase.NoParam<Me>(Dispatchers.IO) {
    override suspend fun execute(): Me =
        loginRepository.getMe()
}