package com.example.myapplication.main.screens.general_screen.general_use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.screens.general_screen.Photos
import kotlinx.coroutines.launch

class GeneralViewModel : ViewModel() {
    private val getPhotoUseCase = GetPhotoUseCase(PhotoApiService())

    private val _photoFlow = MutableLiveData<List<Photos>>()
    val photoFlow: LiveData<List<Photos>>
        get() = _photoFlow

    init {
        viewModelScope.launch {
            val execute = getPhotoUseCase.execute()
            _photoFlow.postValue(execute)
        }
    }


}