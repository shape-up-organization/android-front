package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

class SignInViewModel : ViewModel() {
    lateinit var navigator: Navigator

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    private fun clearFormData() {
        email.value = ""
        password.value = ""
    }

    private fun signIn() {
        println("email: ${email.value}")
        println("password: ${password.value}")

        navigator.navigate(Screen.Feed)
    }

    val handlers = SignInFormHandlers(
        clearFormData = ::clearFormData,
        signIn = ::signIn
    )
}

class SignInFormData(
    val email: MutableState<String>,
    val password: MutableState<String>
)

val signInFormDataMock = SignInFormData(
    email = mutableStateOf(""),
    password = mutableStateOf("")
)

class SignInFormHandlers(
    val clearFormData: () -> Unit,
    val signIn: () -> Unit
)

val signInFormHandlersMock = SignInFormHandlers(
    clearFormData = {},
    signIn = {}
)
