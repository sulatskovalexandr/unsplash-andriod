package com.unsplash.sulatskov.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open fun onViewCreated() {}
}