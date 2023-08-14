package com.example.myapplication.general_screen.presentation.general_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.general_screen.data.repository.PhotoRepositoryImpl
import com.example.myapplication.general_screen.domain.general_usecases.GetPhotoUseCase
import com.example.myapplication.main.presentation.general_screen.Photos
import com.example.myapplication.services.PhotoApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeneralViewModel : ViewModel() {

    private val photoRepository = PhotoRepositoryImpl(PhotoApiService())
    private val getPhotoUseCase = GetPhotoUseCase(photoRepository)

    private val _photoList: MutableStateFlow<List<Photos>?> = MutableStateFlow(null)
    val photoList: StateFlow<List<Photos>?> = _photoList.asStateFlow()

    private var currentPage = 1
    private var isLoading = false

    init {
        loadPhoto(currentPage)
    }

    private fun loadPhoto(page: Int) {
        viewModelScope.launch {
            _photoList.value = getPhotoUseCase.execute(page)
            isLoading = false
            currentPage = page + 1
        }
    }

    fun onRefreshPhotos() {
        currentPage = 1
        loadPhoto(currentPage)
    }

    fun onLoadPhotos() {
        if (!isLoading) {
            isLoading = true
            loadPhoto(currentPage)
        }
    }
}
