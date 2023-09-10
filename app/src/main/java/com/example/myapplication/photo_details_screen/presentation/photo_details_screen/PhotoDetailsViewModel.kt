package com.example.myapplication.photo_details_screen.presentation.photo_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.NetworkChecker
import com.example.myapplication.general_screen.domain.dto.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoDetailsUseCase
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoStatisticsUseCase
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.PhotoDownloadUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhotoDetailsViewModel @Inject constructor(
    private val getPhotoDetails: GetPhotoDetailsUseCase,
    private val getPhotoStatistics: GetPhotoStatisticsUseCase,
    private val photoDownloadUrl: PhotoDownloadUrl,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: Flow<PhotoDetails> = _photoDetails.filterNotNull()

    private val _photoStatistics: MutableStateFlow<PhotoStatistics?> = MutableStateFlow(null)
    val photoStatistics: Flow<PhotoStatistics> = _photoStatistics.filterNotNull()

    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
            if (networkChecker.isNetworkConnected()) {
                _photoDetails.value = getPhotoDetails.execute(photoId = photoId)
            } else {
//                Загружать из кэша
            }
        }
    }

    private fun loadStatisticsPhoto(photoId: String) {
        viewModelScope.launch {
            if (networkChecker.isNetworkConnected()) {
                _photoStatistics.value = getPhotoStatistics.execute(photoId = photoId)
            } else {
//                Загружать из кэша
            }
        }
    }


    fun setPhotoId(photoId: String) {
        loadDetailsPhoto(photoId)
        loadStatisticsPhoto(photoId)
    }

    fun onDownloadClick(photoId: String) {
        viewModelScope.launch {
            photoDownloadUrl(photoId)
        }
    }
}