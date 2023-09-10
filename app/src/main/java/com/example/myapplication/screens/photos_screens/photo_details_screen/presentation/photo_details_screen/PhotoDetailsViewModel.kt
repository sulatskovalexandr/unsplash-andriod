package com.example.myapplication.screens.photos_screens.photo_details_screen.presentation.photo_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Messages
import com.example.myapplication.common.NetworkChecker
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.GetDataBasePhotoDetailsUseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.GetPhotoDetailsUseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.GetPhotoStatisticsUseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.PhotoDownloadUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val getPhotoDetails: GetPhotoDetailsUseCase,
    private val getPhotoStatistics: GetPhotoStatisticsUseCase,
    private val photoDownloadUrl: PhotoDownloadUrl,
    private val networkChecker: NetworkChecker,
    private val getDataBasePhotoDetailsUseCase: GetDataBasePhotoDetailsUseCase

) : ViewModel() {

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: Flow<PhotoDetails> = _photoDetails.filterNotNull()

    private val _photoStatistics: MutableStateFlow<PhotoStatistics?> = MutableStateFlow(null)
    val photoStatistics: Flow<PhotoStatistics> = _photoStatistics.filterNotNull()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
            if (networkChecker.isNetworkConnected()) {
                val execute: PhotoDetails? = getPhotoDetails.execute(photoId = photoId)
                _photoDetails.value = execute
            } else {
                getDataBasePhotoDetailsUseCase.invoke(photoId)
//
            }
        }
    }

    private fun loadStatisticsPhoto(photoId: String) {
        viewModelScope.launch {
            if (networkChecker.isNetworkConnected()) {
                _photoStatistics.value = getPhotoStatistics.execute(photoId = photoId)
            } else {
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }


    fun setPhotoId(photoId: String) {
        loadDetailsPhoto(photoId)
        loadStatisticsPhoto(photoId)
    }

    fun onDownloadClick(photoId: String) {
        viewModelScope.launch {
//            if (networkChecker.isNetworkConnected()) {
//                val isDownloaded = photoExists.run(photoId)
//                if (isDownloaded) {
//                    _messageFlow.value = Messages.AlreadyDownloaded
//                } else {
            photoDownloadUrl(photoId)
//                }
//            } else {
//                _messageFlow.value = "error"
//            }
        }
    }

    fun clearMessage() {
        _messageFlow.value = null
    }
}