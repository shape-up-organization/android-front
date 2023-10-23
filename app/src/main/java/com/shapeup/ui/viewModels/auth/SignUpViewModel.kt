package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.auth.ConfirmEmailPayload
import com.shapeup.api.services.auth.ConfirmEmailStatement
import com.shapeup.api.services.auth.EAuthApi
import com.shapeup.api.services.auth.SendEmailCodePayload
import com.shapeup.api.services.auth.SendEmailCodeStatement
import com.shapeup.api.services.auth.SignUpPayload
import com.shapeup.api.services.auth.SignUpStatement
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.helpers.DateHelper
import com.shapeup.ui.utils.helpers.Navigator
import io.ktor.http.HttpStatusCode

class SignUpViewModel : ViewModel() {
    lateinit var navigator: Navigator
    lateinit var sharedData: SharedData

    private val dateHelper = DateHelper()

    val activeStep = mutableIntStateOf(1)
    val birth = mutableStateOf("")
    val cellPhone = mutableStateOf("")
    val email = mutableStateOf("")
    val name = mutableStateOf("")
    val lastName = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirmation = mutableStateOf("")
    val username = mutableStateOf("")
    val code = mutableStateOf("")

    private fun clearFormData() {
        activeStep.intValue = 1
        birth.value = ""
        cellPhone.value = ""
        email.value = ""
        name.value = ""
        lastName.value = ""
        password.value = ""
        passwordConfirmation.value = ""
        username.value = ""
        code.value = ""
    }

    private suspend fun signUp(): SignUpStatement {
        val authApi = EAuthApi.create(sharedData)

        val payload = SignUpPayload(
            birth = dateHelper.fromFormFieldDateStringToServiceDate(
                value = birth.value,
                pattern = "dd/MM/yyyy"
            ),
            cellPhone = cellPhone.value,
            email = email.value,
            name = name.value,
            lastName = lastName.value,
            password = password.value,
            username = username.value
        )

        return authApi.signUp(payload)
    }

    private suspend fun sendEmailCode(): SendEmailCodeStatement {
        val authApi = EAuthApi.create(sharedData)

        val payload = SendEmailCodePayload(
            email = email.value
        )

        return authApi.sendEmailCode(payload)
    }

    private suspend fun confirmEmail(): ConfirmEmailStatement {
        val authApi = EAuthApi.create(sharedData)

        val payload = ConfirmEmailPayload(
            email = email.value,
            code = code.value
        )

        return authApi.confirmEmail(payload)
    }

    val handlers = SignUpFormHandlers(
        clearFormData = ::clearFormData,
        signUp = ::signUp,
        sendEmailCode = ::sendEmailCode,
        confirmEmail = ::confirmEmail
    )
}

class SignUpFormData(
    val birth: MutableState<String>,
    val cellPhone: MutableState<String>,
    val email: MutableState<String>,
    val name: MutableState<String>,
    val lastName: MutableState<String>,
    val password: MutableState<String>,
    val passwordConfirmation: MutableState<String>,
    val username: MutableState<String>,
    val code: MutableState<String>
)

val signUpFormDataMock = SignUpFormData(
    birth = mutableStateOf(""),
    cellPhone = mutableStateOf(""),
    email = mutableStateOf(""),
    name = mutableStateOf(""),
    lastName = mutableStateOf(""),
    password = mutableStateOf(""),
    passwordConfirmation = mutableStateOf(""),
    username = mutableStateOf(""),
    code = mutableStateOf("")
)

class SignUpFormHandlers(
    val clearFormData: () -> Unit,
    val signUp: suspend () -> SignUpStatement,
    val sendEmailCode: suspend () -> SendEmailCodeStatement,
    val confirmEmail: suspend () -> ConfirmEmailStatement
)

val signUpFormHandlersMock = SignUpFormHandlers(
    clearFormData = {},
    signUp = suspend {
        SignUpStatement(
            status = HttpStatusCode.ServiceUnavailable
        )
    },
    sendEmailCode = suspend {
        SendEmailCodeStatement(
            status = HttpStatusCode.ServiceUnavailable
        )
    },
    confirmEmail = suspend {
        ConfirmEmailStatement(
            status = HttpStatusCode.ServiceUnavailable
        )
    }
)
