package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun VerificationCodeStepPreview() {
    ShapeUpTheme {
        VerificationCodeStep(
            data = VerificationCodeStepFormData(
                verificationCode = mutableStateOf("")
            )
        )
    }
}

class VerificationCodeStepFormData(
    val verificationCode: MutableState<String>
)

@Composable
fun VerificationCodeStep(data: VerificationCodeStepFormData) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            text = buildAnnotatedString {
                append("We will send a verification code to your e-mail address ")
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                    )
                ) {
                    append("example@gmail.com")
                }
                append(".")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            focusManager = focusManager,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            label = "Verification code",
            onValueChange = { data.verificationCode.value = it },
            supportingText = "",
            value = data.verificationCode.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { /*TODO*/ }) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "Resend code"
            )
        }
    }
}
