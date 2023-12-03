package com.shapeup.ui.screens.logged.settings

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.api.services.users.UserFieldPayload
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ChangeNumberPreview() {
    ShapeUpTheme {
        ChangeNumberScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun ChangeNumberScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var isLoading by remember { mutableStateOf(false) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var cellPhone by remember { mutableStateOf(journeyData.userData.value.cellPhone) }
    var cellPhoneError by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    fun updateNumber() {
        coroutine.launch {
            isLoading = true
            val response = journeyHandlers.updateSettings(
                UserFieldPayload(
                    cellPhone = cellPhone
                )
            )
            isLoading = false

            when (response.status) {
                HttpStatusCode.OK -> {
                    navigator.navigateBack()
                }

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                }
            }
        }
    }

    fun validatePhone(): String {
        cellPhoneError = when {
            cellPhone.isBlank() -> context.getString(R.string.txt_errors_required)
            cellPhone.length < 3 -> context.getString(R.string.txt_errors_invalid)

            else -> ""
        }
        return cellPhoneError
    }

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
                text = stringResource(R.string.txt_change_number_title)
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
                    errorText = cellPhoneError,
                    focusManager = focusManager,
                    label = stringResource(R.string.txt_change_number_new),
                    onValueChange = {
                        cellPhone = it
                        validatePhone()
                    },
                    supportingText = "",
                    type = FormFieldType.PHONE,
                    value = cellPhone
                )
            }
        }

        Button(
            enabled = !isLoading && cellPhoneError.isBlank(),
            modifier = Modifier.fillMaxWidth(),
            onClick = { updateNumber() }
        ) {
            if (isLoading) {
                Loading()
            } else {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.txt_change_email_save_changes)
                )
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


