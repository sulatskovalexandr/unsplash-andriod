package com.example.myapplication.domain.use_case.user_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<String, User>(Dispatchers.IO) {

    override suspend fun execute(userName: String): User =
        userRepository.getUser(userName)

}