package com.example.myapplication.photo_details_screen.presentation.photo_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.photo_details_screen.data.repository.PhotoDetailsRepositoryImpl
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoDetailsById
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoStatisticsUseCase
import com.example.myapplication.services.PhotoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PhotoDetailsViewModel : ViewModel() {
    private val photoDetailsRepository = PhotoDetailsRepositoryImpl(PhotoApiService())
    private val getPhotoDetails = GetPhotoDetailsById(photoDetailsRepository)
    private val getPhotoStatistics = GetPhotoStatisticsUseCase(photoDetailsRepository)

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: Flow<PhotoDetails> = _photoDetails.filterNotNull()

    private val _photoStatistics: MutableStateFlow<PhotoStatistics?> = MutableStateFlow(null)
    val photoStatistics: Flow<PhotoStatistics> = _photoStatistics.filterNotNull()


    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
            _photoDetails.value = getPhotoDetails.execute(photoId = photoId)
        }
    }

    private fun loadStatisticsPhoto(photoId: String) {
        viewModelScope.launch {
            _photoStatistics.value = getPhotoStatistics.execute(photoId = photoId)
        }
    }

    fun setPhotoId(photoId: String) {
        loadDetailsPhoto(photoId)
        loadStatisticsPhoto(photoId)
    }
}