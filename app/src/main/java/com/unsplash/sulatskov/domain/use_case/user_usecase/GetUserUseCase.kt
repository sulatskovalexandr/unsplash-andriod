package com.unsplash.sulatskov.domain.use_case.user_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<String, User>(Dispatchers.IO) {

    override suspend fun execute(userName: String): User =
        userRepository.getUser(userName)
}