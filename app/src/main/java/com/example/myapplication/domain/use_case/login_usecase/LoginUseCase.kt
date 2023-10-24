package com.example.myapplication.domain.use_case.login_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) :
    UseCase<String, String>(Dispatchers.IO) {
    override suspend fun execute(code: String): String =
        loginRepository.getAccessToken(code).accessToken
}