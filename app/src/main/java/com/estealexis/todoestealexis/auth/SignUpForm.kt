package com.estealexis.todoestealexis.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpForm(
    @SerialName("firstname")
    var firstname: String = "",
    @SerialName("lastname")
    var lastname: String = "",
    @SerialName("email")
    var email: String = "",
    @SerialName("password")
    var password: String = "",
    @SerialName("password_confirmation")
    var password_confirmation: String = ""
) : java.io.Serializable