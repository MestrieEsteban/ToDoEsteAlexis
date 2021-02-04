package com.estealexis.todoestealexis.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("email")
    val email: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String,
    @SerialName("avatar")
    val avatar: String = ""
)