package com.example.myapplication.domain.use_case.user_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserCollectionUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<UserPhotoParam, List<com.example.myapplication.domain.model.Collection>>(Dispatchers.IO) {

    override suspend fun execute(param: UserPhotoParam): List<Collection> =
        userRepository.getUserCollection(param.userName, param.page)

}