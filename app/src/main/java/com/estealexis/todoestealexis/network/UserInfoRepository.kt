package com.estealexis.todoestealexis.network

import okhttp3.MultipartBody

class UserInfoRepository {
    private val webService = Api.userWebService

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

}