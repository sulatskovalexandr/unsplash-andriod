package com.unsplash.sulatskov.ui.search_screen

import com.unsplash.sulatskov.domain.use_case.search_usecase.GetSearchPhotoUseCase
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
) : BaseViewModel() {

    private val _queryFLow = MutableStateFlow("")
    val queryFlow: StateFlow<String> = _queryFLow.asStateFlow()

}