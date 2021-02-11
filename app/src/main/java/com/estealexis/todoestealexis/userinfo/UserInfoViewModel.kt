package com.estealexis.todoestealexis.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estealexis.todoestealexis.auth.LoginForm
import com.estealexis.todoestealexis.auth.LoginResponse
import com.estealexis.todoestealexis.network.UserInfo
import com.estealexis.todoestealexis.network.UserInfoRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserInfoViewModel : ViewModel() {
    private val repository = UserInfoRepository()
    private val _user = MutableLiveData<UserInfo>()
    val user: LiveData<UserInfo> = _user
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun loadInfo() {
        viewModelScope.launch {
            _user.value = repository.loadInfo()
        }
    }

    fun updateAvatar(avatar: MultipartBody.Part) {
        viewModelScope.launch {
            val userInfo: UserInfo? = repository.updateAvatar(avatar);
            if (userInfo != null)
                _user.value = userInfo!!;
        }
    }

    fun updateUser(user: UserInfo) {
        viewModelScope.launch {
            val userInfo: UserInfo? = repository.updateUser(user);
            if (userInfo != null)
                _user.value = userInfo!!;
        }
    }

    fun login(user: LoginForm) {
        viewModelScope.launch {
            val userInfo: LoginResponse? = repository.login(user)
            if(userInfo == null) _token.value = "null"
            else _token.value = userInfo?.token!!
        }
    }

    fun signUp(user: LoginForm) {
        viewModelScope.launch {
            val userInfo: LoginResponse? = repository.signUp(user)
            println(userInfo)
        }
    }
}