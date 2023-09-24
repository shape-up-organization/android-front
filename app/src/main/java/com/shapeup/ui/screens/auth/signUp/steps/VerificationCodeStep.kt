package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun VerificationCodeStepPreview() {
    ShapeUpTheme {
        VerificationCodeStep(
            data = VerificationCodeStepFormData(
                email = mutableStateOf("example@gmail.com"),
                verificationCode = mutableStateOf("")
            )
        )
    }
}

class VerificationCodeStepFormData(
    val email: MutableState<String>,
    val verificationCode: MutableState<String>
)

@Composable
fun VerificationCodeStep(data: VerificationCodeStepFormData) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clip(shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(all = 24.dp)
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                text = buildAnnotatedString {
                    append(stringResource(R.string.txt_sign_up_screen_send_code_warning))
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    ) {
                        append(data.email.value)
                    }
                    append(".")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            focusManager = focusManager,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            label = stringResource(R.string.txt_sign_up_screen_verification_code_label),
            onValueChange = { data.verificationCode.value = it },
            supportingText = "",
            value = data.verificationCode.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { /*TODO*/ }) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.txt_sign_in_verification_code_screen_resend_code)
            )
        }
    }
}
