package com.example.testapp.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.UserResponse
import com.example.testapp.data.repository.AppRepository
import com.example.testapp.util.Resource
import kotlinx.coroutines.launch

class UserInfoDialogFragmentViewModel(
    private val repository: AppRepository,
) : ViewModel() {

    private val _userResponse: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val userResponse: LiveData<Resource<UserResponse>>
        get() = _userResponse

    init {
        getUserFromToken()
    }

    private fun getUserFromToken() {
        viewModelScope.launch {
            val token = loadUserToken()
            if (token != null) {
                getUserInfo(token)
            }
        }
    }

    private fun getUserInfo(token: String) {
        viewModelScope.launch {
            _userResponse.value = Resource.Loading
            _userResponse.value = repository.getUser(token)
        }
    }

    private suspend fun loadUserToken(): String? {
        return repository.loadUserToken()
    }
}