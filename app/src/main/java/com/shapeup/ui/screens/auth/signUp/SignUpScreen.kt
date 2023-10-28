package com.shapeup.ui.screens.auth.signUp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.screens.auth.signUp.steps.EmailStep
import com.shapeup.ui.screens.auth.signUp.steps.EmailStepFormData
import com.shapeup.ui.screens.auth.signUp.steps.PasswordStep
import com.shapeup.ui.screens.auth.signUp.steps.PasswordStepFormData
import com.shapeup.ui.screens.auth.signUp.steps.PersonalDataStep
import com.shapeup.ui.screens.auth.signUp.steps.PersonalDataStepFormData
import com.shapeup.ui.screens.auth.signUp.steps.PhoneStep
import com.shapeup.ui.screens.auth.signUp.steps.PhoneStepFormData
import com.shapeup.ui.screens.auth.signUp.steps.TermsAndPrivacyStep
import com.shapeup.ui.screens.auth.signUp.steps.UsernameStep
import com.shapeup.ui.screens.auth.signUp.steps.UsernameStepFormData
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.AuthData
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authDataMock
import com.shapeup.ui.viewModels.auth.authHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SignUpPreview() {
    ShapeUpTheme {
        SignUpScreen(
            activeStep = mutableIntStateOf(1),
            data = authDataMock,
            handlers = authHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun SignUpScreen(
    activeStep: MutableState<Int>,
    data: AuthData,
    handlers: AuthHandlers,
    navigator: Navigator
) {
    val hasError = remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = stringResource(R.string.txt_sign_up_screen_progress_bar),
        targetValue = Step.getStepProgress(activeStep = activeStep.value)
    )
    val coroutine = rememberCoroutineScope()

    suspend fun signUp() {
        hasError.value = true
        val response = handlers.signUp()

        println(response)

        when (response.status) {
            HttpStatusCode.Created -> navigator.navigate(Screen.AccountVerification)

            else -> hasError.value = false
        }
    }

    BackHandler {
        Step.returnStep(
            activeStep = activeStep,
            clearData = handlers.clearFormData,
            navigator = navigator
        )
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                enabled = !(hasError.value && Step.isAtLastStep(activeStep.value)),
                onClick = {
                Step.returnStep(
                    activeStep = activeStep,
                    clearData = handlers.clearFormData,
                    navigator = navigator
                )
            }) {
                Icon(
                    contentDescription = stringResource(
                        when (activeStep.value) {
                            1 -> R.string.icon_close
                            else -> R.string.icon_arrow_back
                        }
                    ),
                    painter = painterResource(
                        when (activeStep.value) {
                            1 -> Icon.Close.value
                            else -> Icon.ArrowBack.value
                        }
                    ),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            LinearProgressIndicator(
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .fillMaxWidth()
                    .height(16.dp),
                progress = animatedProgress
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Step.Composable(
                activeStep = activeStep.value,
                data = data,
                hasError = hasError,
                handlers = handlers
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = !hasError.value,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutine.launch {
                    Step.advanceStep(
                        activeStep = activeStep,
                        onFinish = { signUp() }
                    )
                }
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(
                    when (activeStep.value < Step.getMaxSteps()) {
                        true -> R.string.txt_sign_up_screen_continue_button
                        else -> R.string.txt_sign_up_screen_finish_button
                    }
                )
            )
        }
    }
}

enum class Step(
    val step: Int,
    val composable: @Composable (
        data: AuthData,
        hasError: MutableState<Boolean>,
        handlers: AuthHandlers
    ) -> Unit
) {
    PersonalData(
        step = 1,
        composable = { data, hasError, _ ->
            PersonalDataStep(
                data = PersonalDataStepFormData(
                    birth = data.birth,
                    name = data.name,
                    lastName = data.lastName
                ),
                hasError = hasError
            )
        }),
    Email(
        step = 2,
        composable = { data, hasError, _ ->
            EmailStep(
                EmailStepFormData(
                    email = data.email
                ),
                hasError = hasError
            )
        }),
    Phone(
        step = 3,
        composable = { data, hasError, _ ->
            PhoneStep(
                PhoneStepFormData(
                    cellPhone = data.cellPhone
                ),
                hasError = hasError,
            )
        }),
    Username(
        step = 4,
        composable = { data, hasError, _ ->
            UsernameStep(
                UsernameStepFormData(
                    username = data.username
                ),
                hasError = hasError
            )
        }),
    Password(
        step = 5,
        composable = { data, hasError, _ ->
            PasswordStep(
                PasswordStepFormData(
                    password = data.password,
                    passwordConfirmation = data.passwordConfirmation
                ),
                hasError = hasError
            )
        }),
    TermsAndPrivacy(
        step = 6,
        composable = { _, _, _ ->
            TermsAndPrivacyStep()
        });

    companion object {
        fun isAtLastStep(activeStep: Int): Boolean {
            return activeStep == getMaxSteps()
        }

        suspend fun advanceStep(
            activeStep: MutableState<Int>,
            onFinish: suspend () -> Unit
        ) {
            if (isAtLastStep(activeStep.value)) {
                onFinish()
                return
            }

            activeStep.value += 1
        }

        fun returnStep(
            activeStep: MutableState<Int>,
            clearData: () -> Unit,
            navigator: Navigator
        ) {
            if (activeStep.value > 1) {
                activeStep.value -= 1
                return
            }

            clearData()
            navigator.navigateBack()
        }

        fun getMaxSteps(): Int {
            return enumValues<Step>().maxByOrNull { it.step }?.step ?: 0
        }

        fun getStepProgress(activeStep: Int): Float {
            return activeStep * 1.0f / getMaxSteps()
        }

        @Composable
        fun Composable(
            activeStep: Int,
            data: AuthData,
            hasError: MutableState<Boolean>,
            handlers: AuthHandlers
        ): Unit? {
            return enumValues<Step>().find { it.step == activeStep }?.composable?.invoke(
                data,
                hasError,
                handlers
            )
        }
    }
}
