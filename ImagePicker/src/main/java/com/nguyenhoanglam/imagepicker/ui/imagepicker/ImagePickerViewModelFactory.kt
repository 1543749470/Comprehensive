/*
 * Copyright (c) 2020 Nguyen Hoang Lam.
 * All rights reserved.
 */

package com.nguyenhoanglam.imagepicker.ui.imagepicker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImagePickerViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImagePickerViewModel::class.java)) {
            val imagePickerViewModel = ImagePickerViewModel(application)
            return imagePickerViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}