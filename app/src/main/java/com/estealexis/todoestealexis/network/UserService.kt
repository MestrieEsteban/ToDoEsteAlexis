package com.estealexis.todoestealexis.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>
}

@Serializable
data class UserInfo(
    @SerialName("email")
    val email: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String
)