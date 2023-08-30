package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    private fun updateEmail(value: String) {
        email = value
    }

    private fun updatePassword(value: String) {
        password = value
    }

    private fun signIn() {
        println("email: $email")
        println("password: $password")
    }

    val handlers = SignInFormHandlers(
        signIn = ::signIn,
        updateEmail = ::updateEmail,
        updatePassword = ::updatePassword
    )
}

class SignInFormData(
    val email: String,
    val password: String
)

class SignInFormHandlers(
    val signIn: () -> Unit,
    val updateEmail: (String) -> Unit,
    val updatePassword: (String) -> Unit
)
