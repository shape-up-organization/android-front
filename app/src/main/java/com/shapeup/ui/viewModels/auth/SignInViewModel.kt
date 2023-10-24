package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.auth.EAuthApi
import com.shapeup.api.services.auth.SignInPayload
import com.shapeup.api.services.auth.SignInStatement
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.constants.Screen
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
        val authApi = EAuthApi.create(sharedData)

        val payload = SignInPayload(
            email = email.value,
            password = password.value
        )

        return authApi.signIn(payload)
    }

    private suspend fun startupVerification() {
//        val jwtTokenShared = sharedData.get("jwtToken")
        val emailShared = sharedData.get("email")
        val passwordShared = sharedData.get("password")

        when {
//            !jwtTokenShared.isNullOrBlank() -> navigator.navigate(Screen.Feed)
            !emailShared.isNullOrBlank() && !passwordShared.isNullOrBlank() -> {
                email.value = emailShared
                password.value = passwordShared

                val response = signIn()

                println(response)

                when (response.status) {
                    HttpStatusCode.OK -> navigator.navigate(Screen.Feed)

                    else -> navigator.navigate(Screen.Welcome)
                }
            }

            else -> navigator.navigate(Screen.Welcome)
        }
    }

    val handlers = SignInFormHandlers(
        clearFormData = ::clearFormData,
        signIn = ::signIn,
        startupVerification = ::startupVerification
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
    val signIn: suspend () -> SignInStatement,
    val startupVerification: suspend () -> Unit
)
val signInFormHandlersMock = SignInFormHandlers(
    clearFormData = {},
    signIn = suspend {
        SignInStatement(
            data = null,
            status = HttpStatusCode.ServiceUnavailable
        )
    },
    startupVerification = suspend { }
)
