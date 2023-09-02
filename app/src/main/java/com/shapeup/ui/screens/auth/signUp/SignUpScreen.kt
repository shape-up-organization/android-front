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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.shapeup.ui.screens.auth.signUp.steps.VerificationCodeStep
import com.shapeup.ui.screens.auth.signUp.steps.VerificationCodeStepFormData
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.SignUpFormData
import com.shapeup.ui.viewModels.auth.SignUpFormHandlers

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SignUpPreview() {
    ShapeUpTheme {
        SignUpScreen(
            activeStep = mutableIntStateOf(1),
            data = SignUpFormData(
                birthday = mutableStateOf(""),
                email = mutableStateOf(""),
                firstName = mutableStateOf(""),
                lastName = mutableStateOf(""),
                password = mutableStateOf(""),
                passwordConfirmation = mutableStateOf(""),
                phone = mutableStateOf(""),
                username = mutableStateOf(""),
                verificationCode = mutableStateOf("")
            ),
            handlers = SignUpFormHandlers(
                clearFormData = {},
                signUp = {},
                verifyCode = {}
            ),
            navigator = Navigator()
        )
    }
}

@Composable
fun SignUpScreen(
    activeStep: MutableState<Int>,
    data: SignUpFormData,
    handlers: SignUpFormHandlers,
    navigator: Navigator
) {
    val animatedProgress by animateFloatAsState(
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "Progress bar indicator",
        targetValue = Step.getStepProgress(activeStep = activeStep.value)
    )

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
            IconButton(onClick = {
                Step.returnStep(
                    activeStep = activeStep,
                    clearData = handlers.clearFormData,
                    navigator = navigator
                )
            }) {
                Icon(
                    contentDescription = "Icon arrow back",
                    painter = painterResource(
                        id = when (activeStep.value) {
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
                data = data
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                Step.advanceStep(
                    activeStep = activeStep,
                    onFinish = handlers.signUp
                )
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Continue"
            )
        }
    }
}

enum class Step(val step: Int, val composable: @Composable (data: SignUpFormData) -> Unit) {
    PersonalData(step = 1, composable = {
        PersonalDataStep(
            PersonalDataStepFormData(
                birthday = it.birthday,
                firstName = it.firstName,
                lastName = it.lastName
            )
        )
    }),
    Email(step = 2, composable = {
        EmailStep(
            EmailStepFormData(email = it.email)
        )
    }),
    VerificationCode(step = 3, composable = {
        VerificationCodeStep(
            VerificationCodeStepFormData(
                verificationCode = it.verificationCode
            )
        )
    }),
    Phone(step = 4, composable = {
        PhoneStep(
            PhoneStepFormData(
                phone = it.phone
            )
        )
    }),
    Username(step = 5, composable = {
        UsernameStep(
            UsernameStepFormData(
                username = it.username
            )
        )
    }),
    Password(step = 6, composable = {
        PasswordStep(
            PasswordStepFormData(
                password = it.password,
                passwordConfirmation = it.passwordConfirmation
            )
        )
    }),
    TermsAndPrivacy(step = 7, composable = {
        TermsAndPrivacyStep()
    });

    companion object {
        fun advanceStep(activeStep: MutableState<Int>, onFinish: () -> Unit) {
            if (activeStep.value < getMaxSteps()) {
                activeStep.value += 1
                return
            }

            onFinish()
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
            return activeStep * 1.0f / Step.getMaxSteps()
        }

        @Composable
        fun Composable(data: SignUpFormData, activeStep: Int): Unit? {
            return enumValues<Step>().find { it.step == activeStep }?.composable?.invoke(data)
        }
    }
}
