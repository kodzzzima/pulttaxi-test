package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.data.repository.AppRepository
import com.example.testapp.ui.inputCode.InputCodeFragmentViewModel
import com.example.testapp.ui.inputNumber.InputNumberFragmentViewModel
import com.example.testapp.ui.userInfo.UserInfoDialogFragmentViewModel

class ViewModelFactory(private val appRepository: AppRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputNumberFragmentViewModel::class.java)) {
            return InputNumberFragmentViewModel(appRepository) as T
        }

        if (modelClass.isAssignableFrom(InputCodeFragmentViewModel::class.java)) {
            return InputCodeFragmentViewModel(appRepository) as T
        }

        if (modelClass.isAssignableFrom(UserInfoDialogFragmentViewModel::class.java)) {
            return UserInfoDialogFragmentViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}