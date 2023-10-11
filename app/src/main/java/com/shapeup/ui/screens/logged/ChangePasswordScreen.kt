package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator

@SuppressLint("UnrememberedMutableState")
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
        navigator.navigateBack()
    }

    Column(

        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header(
                navigateTo = { navigator.navigateBack() },
                text = "Change Password"
            )

            IconButton(onClick = { navigator.navigateBack() }) {
                Icon(
                    contentDescription = stringResource(Icon.ArrowBack.description),
                    painter = painterResource(Icon.ArrowBack.value),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (""),
                    supportingText = null,
                    value = "Check the code in your email"
                )

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (""),
                    supportingText = null,
                    value = "Old password"
                )

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (""),
                    supportingText = null,
                    value = "New password"
                )

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (""),
                    supportingText = null,
                    value = "Confirm your password"
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    TextButton(onClick = {
                        //* TO DO *\\
                    }
                    ) {

                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = ("Forgot Old password?"),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // * TO DO *\\
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Save Changes"
            )
        }
    }
}


