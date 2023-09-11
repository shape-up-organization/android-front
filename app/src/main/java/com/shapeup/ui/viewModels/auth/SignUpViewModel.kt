package com.shapeup.ui.viewModels.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.DateHelper
import com.shapeup.ui.utils.helpers.Navigator

class SignUpViewModel : ViewModel() {
    var navigator: Navigator? = null

    val activeStep = mutableIntStateOf(1)
    val birthday = mutableStateOf("")
    val email = mutableStateOf("")
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirmation = mutableStateOf("")
    val phone = mutableStateOf("")
    val username = mutableStateOf("")
    val verificationCode = mutableStateOf("")

    val dateHelper = DateHelper()

    private fun clearFormData() {
        activeStep.intValue = 1
        birthday.value = ""
        email.value = ""
        firstName.value = ""
        lastName.value = ""
        password.value = ""
        passwordConfirmation.value = ""
        phone.value = ""
        username.value = ""
        verificationCode.value = ""
    }

    private fun signUp() {
        println("activeStep: ${activeStep.intValue}")
        println(
            "birthday: ${
            when {
                birthday.value != "" -> dateHelper.fromFormFieldDateStringToServiceDate(
                    birthday.value
                )

                else -> ""
            }
            }"
        )
        println("email: ${email.value}")
        println("firstName: ${firstName.value}")
        println("lastName: ${lastName.value}")
        println("password: ${password.value}")
        println("passwordConfirmation: ${passwordConfirmation.value}")
        println("phone: ${phone.value}")
        println("username: ${username.value}")
        println("verificationCode: ${verificationCode.value}")

        this.navigator?.navigate?.invoke(Screen.Feed)
    }

    private fun verifyCode() {}

    val handlers = SignUpFormHandlers(
        clearFormData = ::clearFormData,
        signUp = ::signUp,
        verifyCode = ::verifyCode
    )
}

class SignUpFormData(
    val birthday: MutableState<String>,
    val email: MutableState<String>,
    val firstName: MutableState<String>,
    val lastName: MutableState<String>,
    val password: MutableState<String>,
    val passwordConfirmation: MutableState<String>,
    val phone: MutableState<String>,
    val username: MutableState<String>,
    val verificationCode: MutableState<String>
)

class SignUpFormHandlers(
    val clearFormData: () -> Unit,
    val signUp: () -> Unit,
    val verifyCode: () -> Unit
)
