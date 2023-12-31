package com.example.myapplication.domain.use_case.user_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.UserPhoto
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserPhotoUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<UserPhotoParam, List<UserPhoto>>(Dispatchers.IO) {

    override suspend fun execute(param: UserPhotoParam): List<UserPhoto> =
        userRepository.getUserPhoto(param.userName, param.page)
}

data class UserPhotoParam(
    val userName: String,
    val page: Int,
)