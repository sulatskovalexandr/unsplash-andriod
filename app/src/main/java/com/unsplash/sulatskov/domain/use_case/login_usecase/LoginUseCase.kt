package com.unsplash.sulatskov.domain.use_case.login_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) :
    UseCase<String, String>(Dispatchers.IO) {
    override suspend fun execute(code: String): String =
        loginRepository.getAccessToken(code).accessToken
}