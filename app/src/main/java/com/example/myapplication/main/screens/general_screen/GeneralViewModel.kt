package com.example.myapplication.main.screens.general_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.screens.general_screen.general_use_case.GetPhotoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeneralViewModel : ViewModel() {
    private val getPhotoUseCase = GetPhotoUseCase(PhotoApiService())

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
