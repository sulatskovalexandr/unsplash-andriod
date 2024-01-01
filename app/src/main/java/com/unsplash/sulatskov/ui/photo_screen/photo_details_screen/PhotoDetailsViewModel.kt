package com.unsplash.sulatskov.ui.photo_screen.photo_details_screen

import androidx.lifecycle.viewModelScope
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.common.NetworkChecker
import com.unsplash.sulatskov.domain.model.PhotoDetails
import com.unsplash.sulatskov.domain.model.PhotoStatistics
import com.unsplash.sulatskov.domain.use_case.photo_details_usecase.GetDataBasePhotoDetailsUseCase
import com.unsplash.sulatskov.domain.use_case.photo_details_usecase.GetPhotoDetailsUseCase
import com.unsplash.sulatskov.domain.use_case.photo_details_usecase.GetPhotoStatisticsUseCase
import com.unsplash.sulatskov.domain.use_case.photo_details_usecase.PhotoDownloadUrl
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.delay
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
) : BaseViewModel() {

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: Flow<PhotoDetails> = _photoDetails.filterNotNull()

    private val _photoStatistics: MutableStateFlow<PhotoStatistics?> = MutableStateFlow(null)
    val photoStatistics: Flow<PhotoStatistics> = _photoStatistics.filterNotNull()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private lateinit var photoId: String

    override fun onViewCreated() {
        loadDetailsPhoto(photoId)
        loadStatisticsPhoto(photoId)
    }

    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
//            if (networkChecker.isNetworkConnected()) {
            getPhotoDetails.invoke(photoId)
                .onSuccess {
//                val execute: PhotoDetails? = getPhotoDetails.execute(photoId = photoId)
                    _photoDetails.value = it
                    _messageFlow.value = Messages.HideShimmer
                }.onFailure {
                    delay(1000)
                    getDataBasePhotoDetailsUseCase.invoke(photoId)
                    _messageFlow.value = Messages.HideShimmer

                }
//            } else {

//            }
        }
    }

    private fun loadStatisticsPhoto(photoId: String) {
        viewModelScope.launch {
//            if (networkChecker.isNetworkConnected()) {
            getPhotoStatistics.invoke(photoId)
                .onSuccess {
                    _photoStatistics.value = it
                    _messageFlow.value = Messages.HideShimmer
                }.onFailure {
                    delay(1000)
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
//            } else {

//            }
//        }
        }
    }

    fun setPhotoId(photoId: String) {
        this.photoId = photoId
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