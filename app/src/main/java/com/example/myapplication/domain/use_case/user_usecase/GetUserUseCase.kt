package com.example.myapplication.domain.use_case.user_usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val usersPhotoRepository: UserRepository) {

    suspend fun execute(userName: String): User =
        usersPhotoRepository.getUser(userName)

}