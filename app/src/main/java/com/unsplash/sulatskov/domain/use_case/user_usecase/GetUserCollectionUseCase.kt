package com.unsplash.sulatskov.domain.use_case.user_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserCollectionUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<UserPhotoParam, List<Collection>>(Dispatchers.IO) {

    override suspend fun execute(param: UserPhotoParam): List<Collection> =
        userRepository.getUserCollection(param.userName, param.page)
}