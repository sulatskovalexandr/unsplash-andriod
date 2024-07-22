package com.unsplash.sulatskov.ui.collection_screens

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.use_case.collection_usecase.GetCollectionUseCase
import com.unsplash.sulatskov.domain.use_case.collection_usecase.GetDataBaseCollectionUseCase
import com.unsplash.sulatskov.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    getCollectionUseCase: GetCollectionUseCase
) : BaseViewModel() {

    val listCollection: Flow<PagingData<CollectionDto>> =
        getCollectionUseCase.executePagingCollection()
            .cachedIn(viewModelScope)
}