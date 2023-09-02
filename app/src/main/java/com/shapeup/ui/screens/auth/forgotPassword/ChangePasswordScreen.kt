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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
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
    BackHandler {
        navigator.navigateClean(Screen.ForgotPassword)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header { navigator.navigate(Screen.ForgotPassword) }

        Content()

        Footer()
    }
}

@Composable
private fun Content() {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            text = "Change Password"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            FormField(
                label = "New Password",
                value = "",
                supportingText = "",
                onValueChange = {},
                trailingIcon = Icon.EyeOpen,
                iconDescription = "Show password icon"
            )
            FormField(
                label = "Confirm Password",
                value = "",
                supportingText = "",
                onValueChange = {},
                trailingIcon = Icon.EyeOpen,
                iconDescription = "Show password icon"
            )
        }
    }
}

@Composable
private fun Footer() {
    Button(
        onClick = { /*TODO*/ },
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Finish",
            Modifier
                .padding(vertical = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
