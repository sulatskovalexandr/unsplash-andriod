package com.example.myapplication.main.screens.photo_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.model.PhotoDetails
import com.example.myapplication.main.screens.photo_details_screen.photo_details_use_case.GetPhotoDetailsById
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PhotoDetailsViewModel : ViewModel() {
    private val getPhotoDetails = GetPhotoDetailsById(PhotoApiService())

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: Flow<PhotoDetails> = _photoDetails.filterNotNull()


    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
            _photoDetails.value = getPhotoDetails.execute(photoId = photoId)
        }
    }

    fun setPhotoId(photoId: String) {
        loadDetailsPhoto(photoId)
    }
}