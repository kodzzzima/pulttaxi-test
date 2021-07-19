package com.example.testapp.ui.inputCode

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.TokenResponse
import com.example.testapp.data.repository.AppRepository
import com.example.testapp.util.Resource
import kotlinx.coroutines.launch

class InputCodeFragmentViewModel(
    private val repository: AppRepository,
) : ViewModel() {

    private val _tokenResponse: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val tokenResponse: LiveData<Resource<TokenResponse>>
        get() = _tokenResponse

    private val _timerFlag = MutableLiveData(false)
    val timerFlag: LiveData<Boolean>
        get() = _timerFlag

    var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun enterCode(phoneNumber: String, smsCode: String) {
        viewModelScope.launch {
            _tokenResponse.value = Resource.Loading
            _tokenResponse.value = repository.getToken(phoneNumber, smsCode)
        }
    }

    fun repeatSmsCode() {
        viewModelScope.launch {
            loadUserPhone()?.let { repository.getSmsCode(it) }
        }
    }

    suspend fun saveUserToken(token: String) {
        repository.saveUserToken(token)
    }

    suspend fun loadUserPhone(): String? {
        return repository.loadUserPhone()
    }

    fun startTimer() {
        _timerFlag.value = false
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                _timerFlag.value = true
            }
        }.start()
    }
}