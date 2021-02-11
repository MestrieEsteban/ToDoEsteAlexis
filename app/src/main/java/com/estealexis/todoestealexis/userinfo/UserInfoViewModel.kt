package com.estealexis.todoestealexis.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.auth.LoginForm
import com.estealexis.todoestealexis.auth.LoginResponse
import com.estealexis.todoestealexis.auth.SignUpForm
import com.estealexis.todoestealexis.auth.SignUpResponse
import com.estealexis.todoestealexis.network.UserInfo
import com.estealexis.todoestealexis.network.UserInfoRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import kotlin.reflect.typeOf

class UserInfoViewModel : ViewModel() {
    private val repository = UserInfoRepository()
    private val _login_user = MutableLiveData<UserInfo>()
    val login_user: LiveData<UserInfo> = _login_user
    private val _sign_up_user = MutableLiveData<SignUpResponse>()
    val sign_up_user: LiveData<SignUpResponse> = _sign_up_user
    private val _token = MutableLiveData<LoginResponse>()
    val token: LiveData<LoginResponse> = _token

    fun loadInfo() {
        viewModelScope.launch {
            _login_user.value = repository.loadInfo()
        }
    }

    fun updateAvatar(avatar: MultipartBody.Part) {
        viewModelScope.launch {
            val userInfo: UserInfo? = repository.updateAvatar(avatar);
            if (userInfo != null)
                _login_user.value = userInfo!!;
        }
    }

    fun updateUser(user: UserInfo) {
        viewModelScope.launch {
            val userInfo: UserInfo? = repository.updateUser(user);
            if (userInfo != null)
                _login_user.value = userInfo!!;
        }
    }

    fun login(user: LoginForm) {
        viewModelScope.launch {
            val userInfo: LoginResponse? = repository.login(user)
            if(userInfo == null) _token.value = LoginResponse(token="")
            else _token.value = userInfo!!
        }
    }

    fun signUp(user: SignUpForm) {
        viewModelScope.launch {
            val userInfo: SignUpResponse? = repository.signUp(user)
            if (userInfo == null) _sign_up_user.value= SignUpResponse(token="",expire= "")
            else  _sign_up_user.value = userInfo!!;
        }
    }

}