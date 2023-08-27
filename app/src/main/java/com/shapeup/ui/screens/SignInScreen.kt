package com.shapeup.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun SignInPreview() {
    ShapeUpTheme {
        SignInScreen(
            navigateToWelcome = {},
            navigateToForgotPassword = {},
            navigateToSignIn = {},
            navigateToSignUp = {}
        )
    }
}

@Composable
fun SignInScreen(
    navigateToWelcome: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(navigateToWelcome)

        Content(navigateToForgotPassword)

        Footer(navigateToSignIn, navigateToSignUp)
    }
}

@Composable
fun Content(
    navigateToForgotPassword: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            text = "Welcome Back"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Form(navigateToForgotPassword)
    }
}

@Composable
fun Form(navigateToForgotPassword: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        FormField(
            labelText = "E-mail",
            value = "",
            supportingText = "",
            onValueChange = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            labelText = "Password",
            value = "",
            supportingText = "",
            onValueChange = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = navigateToForgotPassword) {
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun Footer(
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = navigateToSignIn,
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign in",
                Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = navigateToSignUp,
            Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Sign up",
                Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
