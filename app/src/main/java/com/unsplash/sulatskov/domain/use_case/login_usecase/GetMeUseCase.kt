package com.unsplash.sulatskov.domain.use_case.login_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Me
import com.unsplash.sulatskov.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetMeUseCase @Inject constructor(private val loginRepository: LoginRepository) :
    UseCase.NoParam<Me>(Dispatchers.IO) {
    override suspend fun execute(): Me =
        loginRepository.getMe()
}