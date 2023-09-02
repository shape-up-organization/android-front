package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    private fun signIn() {
        println("email: $email")
        println("password: $password")
    }

    val handlers = SignInFormHandlers(
        signIn = ::signIn
    )
}

class SignInFormData(
    val email: MutableState<String>,
    val password: MutableState<String>
)

class SignInFormHandlers(
    val signIn: () -> Unit
)
