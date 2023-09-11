package com.shapeup.ui.screens.auth.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun ForgotPasswordPreview() {
    ShapeUpTheme {
        ForgotPasswordScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun ForgotPasswordScreen(
    navigator: Navigator
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(navigateTo = { navigator.navigateBack() })

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.txt_forgot_password_screen_title)
            )

            Spacer(modifier = Modifier.height(48.dp))

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
                    text = stringResource(R.string.txt_forgot_password_screen_info),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                label = stringResource(R.string.txt_forgot_password_screen_email_input_label),
                onValueChange = { /*TODO*/ },
                supportingText = "",
                value = ""
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navigator.navigate(Screen.SignInVerificationCode) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.txt_forgot_password_screen_send)
            )
        }
    }
}
