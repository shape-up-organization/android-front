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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.toZipCodeMask
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ChangeAddressPreview() {
    ShapeUpTheme {
        ChangeAddressScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun ChangeAddressScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var isLoading by remember { mutableStateOf(false) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    var gotAddress by remember { mutableStateOf(false) }
    var zipCode by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var complement by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    fun getAddressByZipCode() {
        if (gotAddress) return

        coroutine.launch {
            isLoading = true
            val response = journeyHandlers.getAddressByZipCode(zipCode.replace("-", ""))
            isLoading = false

            when (response.status) {
                HttpStatusCode.OK -> {
                    gotAddress = true

                    if (response.data != null) {
                        street = response.data.street
                        city = response.data.city
                        district = response.data.district
                        state = response.data.state
                    }
                }

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
                text = stringResource(R.string.txt_change_address_title)
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
            modifier = Modifier.weight(1f),
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
                    label = (stringResource(R.string.txt_change_address_zip_code)),
                    onValueChange = {
                        zipCode = toZipCodeMask(it)
                        if (zipCode.length == 9) {
                            getAddressByZipCode()
                        } else {
                            gotAddress = false
                        }
                    },
                    supportingText = null,
                    value = zipCode
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (stringResource(R.string.txt_change_address_street_name)),
                    onValueChange = { street = it },
                    supportingText = null,
                    value = street
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (stringResource(R.string.txt_change_address_city)),
                    onValueChange = { city = it },
                    supportingText = null,
                    value = city
                )

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (stringResource(R.string.txt_change_address_state)),
                    onValueChange = { state = it },
                    supportingText = null,
                    value = state
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = (stringResource(R.string.txt_change_address_number)),
                    onValueChange = { number = it },
                    supportingText = null,
                    value = number
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    label = (stringResource(R.string.txt_change_address_complement)),
                    onValueChange = { complement = it },
                    supportingText = null,
                    value = complement
                )
            }
        }

        Button(
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                //* TO DO *\\
            }
        ) {
            if (isLoading) {
                Loading()
            } else {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.txt_change_address_save_changes),
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
