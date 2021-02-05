package com.estealexis.todoestealexis.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginForm(
    @SerialName("email")
    var email: String = "",
    @SerialName("password")
    var password: String = "",
) : java.io.Serializable