package com.shapeup.ui.screens.auth.forgotPassword

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun ChangePasswordPreview() {
    ShapeUpTheme {
        ChangePasswordScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun ChangePasswordScreen(
    navigator: Navigator
) {
    val focusManager = LocalFocusManager.current

    BackHandler {
        navigator.navigateClean(Screen.ForgotPassword)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header { navigator.navigateClean(Screen.ForgotPassword) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.txt_change_password_screen_title)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    label = stringResource(R.string.txt_change_password_screen_new_password_label),
                    maxLines = 2,
                    onValueChange = { /*TODO*/ },
                    supportingText = "",
                    type = FormFieldType.PASSWORD,
                    value = ""
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    label = stringResource(
                        R.string.txt_change_password_screen_password_confirmation_label
                    ),
                    maxLines = 2,
                    onValueChange = { /*TODO*/ },
                    supportingText = "",
                    type = FormFieldType.PASSWORD,
                    value = ""
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.txt_change_password_screen_finish)
            )
        }
    }
}
