package com.unsplash.sulatskov.domain.use_case.user_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.UserPhoto
import com.unsplash.sulatskov.domain.repository.UserRepository
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