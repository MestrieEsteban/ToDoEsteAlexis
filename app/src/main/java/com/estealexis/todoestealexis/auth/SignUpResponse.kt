package com.estealexis.todoestealexis.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("token")
    val token: String,
    @SerialName("expire")
    val expire: String,
) : java.io.Serializable