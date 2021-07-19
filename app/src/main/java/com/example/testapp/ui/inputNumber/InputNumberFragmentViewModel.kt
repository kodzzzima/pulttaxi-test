package com.example.testapp.ui.inputNumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.SmsCodeResponse
import com.example.testapp.data.repository.AppRepository
import com.example.testapp.util.Resource
import kotlinx.coroutines.launch

class InputNumberFragmentViewModel(
    private val repository: AppRepository,
) : ViewModel() {

    private val _smsCodeResponse: MutableLiveData<Resource<SmsCodeResponse>> = MutableLiveData()
    val smsCodeResponse: LiveData<Resource<SmsCodeResponse>>
        get() = _smsCodeResponse

    fun enterNumber(phoneNumber: String) {
        viewModelScope.launch {
            _smsCodeResponse.value = Resource.Loading
            _smsCodeResponse.value = repository.getSmsCode(phoneNumber)
        }
    }

    suspend fun saveUserPhone(phoneNumber: String) {
        repository.saveUserPhone(phoneNumber)
    }
}