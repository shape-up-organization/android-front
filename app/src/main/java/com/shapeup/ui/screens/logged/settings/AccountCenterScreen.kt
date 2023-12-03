package com.shapeup.ui.screens.logged.settings

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.RowSettings
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authHandlersMock
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Composable
@Preview
fun AccountCenterScreenPreview() {
    ShapeUpTheme {
        AccountCenterScreen(
            authHandlers = authHandlersMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun AccountCenterScreen(
    authHandlers: AuthHandlers,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var isLoading by remember { mutableStateOf(false) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current

    fun signOut() {
        authHandlers.signOut()
        navigator.navigateClean(Screen.Splash)
    }

    fun deleteAccount() {
        coroutine.launch {
            isLoading = true
            val response = journeyHandlers.deleteAccount()

            when (response.status) {
                HttpStatusCode.OK -> signOut()
                HttpStatusCode.NoContent -> signOut()

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                }
            }
        }
    }

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Header(
            navigateTo = { navigator.navigateBack() },
            text = stringResource(R.string.txt_account_center_title)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
//            RowSettings(
//                text = stringResource(R.string.txt_account_center_change_address),
//                screen = { navigator.navigate(Screen.ChangeAddress) }
//            )
//            Divider(
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier
//                    .fillMaxWidth(),
//                thickness = 1.dp
//            )

            RowSettings(
                text = stringResource(R.string.txt_account_center_change_email),
                screen = { navigator.navigate(Screen.ChangeEmail) }
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            RowSettings(
                text = stringResource(R.string.txt_account_center_change_mobile_number),
                screen = { navigator.navigate(Screen.ChangeNumber) }
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            RowSettings(
                text = stringResource(R.string.txt_account_center_change_birthday),
                screen = { navigator.navigate(Screen.ChangeBirthday) }
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            RowSettings(
                text = stringResource(R.string.txt_account_center_change_password),
                screen = { navigator.navigate(Screen.ChangePasswordSettings) }
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(vertical = 24.dp)
            ) {
                OutlinedButton(
                    enabled = !isLoading,
                    onClick = { signOut() },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = stringResource(R.string.txt_account_center_sign_out),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    enabled = !isLoading,
                    onClick = { deleteAccount() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )

                ) {
                    if (isLoading) {
                        Loading()
                    } else {
                        Text(
                            text = stringResource(R.string.txt_account_center_delete_account),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}
