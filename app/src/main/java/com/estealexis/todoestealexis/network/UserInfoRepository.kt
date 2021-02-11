package com.estealexis.todoestealexis.network

import com.estealexis.todoestealexis.auth.LoginForm
import com.estealexis.todoestealexis.auth.LoginResponse
import com.estealexis.todoestealexis.auth.SignUpForm
import com.estealexis.todoestealexis.auth.SignUpResponse
import okhttp3.MultipartBody

class UserInfoRepository {
    private val webService = Api.INSTANCE.userWebService

    suspend fun loadInfo(): UserInfo? {
        val response = webService.getInfo()
        return if (response.isSuccessful) response.body()!! else null
    }

    suspend fun updateAvatar(avatar: MultipartBody.Part): UserInfo? {
        val response = webService.updateAvatar(avatar);
        return if (response.isSuccessful) response.body()!! else null
    }

    suspend fun updateUser(user: UserInfo): UserInfo? {
        val response = webService.update(user);
        return if (response.isSuccessful) response.body()!! else null
    }
    suspend fun login(user: LoginForm): LoginResponse? {
        val response = webService.login(user);
        return if (response.isSuccessful) response.body()!! else null
    }

    suspend fun signUp(user: SignUpForm): SignUpResponse? {
        val response  = webService.signUp(user);
        return if (response.isSuccessful) response.body()!! else response.body()
    }


}