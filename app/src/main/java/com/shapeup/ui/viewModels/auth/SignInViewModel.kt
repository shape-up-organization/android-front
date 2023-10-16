package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.users.EUsersApi
import com.shapeup.api.services.users.SignInPayload
import com.shapeup.api.services.users.SignInStatement
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.helpers.Navigator
import io.ktor.http.HttpStatusCode

class SignInViewModel : ViewModel() {
    lateinit var navigator: Navigator
    lateinit var sharedData: SharedData

    val email = mutableStateOf("")
    val password = mutableStateOf("")


    private fun clearFormData() {
        email.value = ""
        password.value = ""
    }

    private suspend fun signIn(): SignInStatement {
        val usersApi = EUsersApi.create(sharedData)

        println("email: ${email.value}")
        println("password: ${password.value}")

        val payload = SignInPayload(
            email = email.value,
            password = password.value
        )

        return usersApi.signIn(payload)
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
    val signIn: suspend () -> SignInStatement
)

val signInFormHandlersMock = SignInFormHandlers(
    clearFormData = {},
    signIn = suspend {
        SignInStatement(
            data = null,
            status = HttpStatusCode.ServiceUnavailable
        )
    }
)
