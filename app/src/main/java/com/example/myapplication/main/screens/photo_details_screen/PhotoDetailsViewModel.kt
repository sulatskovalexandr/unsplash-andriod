package com.example.myapplication.main.screens.photo_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.model.PhotoDetails
import com.example.myapplication.main.screens.general_screen.general_use_case.GetPhotoDetailsById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoDetailsViewModel : ViewModel() {
    private val getPhotoDetails = GetPhotoDetailsById(PhotoApiService())

    private val _photoDetails: MutableStateFlow<PhotoDetails?> = MutableStateFlow(null)
    val photoDetails: StateFlow<PhotoDetails?> = _photoDetails.asStateFlow()


//    init {
//        loadDetailsPhoto(photoId )
//    }

    private fun loadDetailsPhoto(photoId: String) {
        viewModelScope.launch {
            _photoDetails.value = getPhotoDetails.execute(photoId = photoId)

        }
    }
}