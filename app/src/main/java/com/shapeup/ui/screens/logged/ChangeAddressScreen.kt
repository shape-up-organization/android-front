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
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock

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
   // var updatedZipCode by remember { mutableStateOf(journeyData.userData.value.firstName) }
    //var updatedStreetName by remember { mutableStateOf(journeyData.userData.value.lastName) }
   // var updatedCityName by remember { mutableStateOf(journeyData.userData.value.username) }
   // var updatedState by remember { mutableStateOf(journeyData.userData.value.bio ?: "") }
   // var updatedContry by remember { mutableStateOf(journeyData.userData.value.bio ?: "") }
   // var updatedNumber by remember { mutableStateOf(journeyData.userData.value.bio ?: "") }
   // var updatedComplement by remember { mutableStateOf(journeyData.userData.value.bio ?: "") }


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
                text = "Change Address"
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
                    label = ("Zip code"),
                    supportingText = null,
                    value = "Zip Code"
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = ("Street Name"),
                    supportingText = null,
                    value = "Street"
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label =("City"),
                    supportingText = null,
                    value = "City"
                )

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = ("State"),
                    supportingText = null,
                    value = "State"
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = ("Contry"),
                    supportingText = null,
                    value = "Contry"
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = ("Number"),
                    supportingText = null,
                    value = "Number"
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    label = ("Complement"),
                    supportingText = null,
                    value = "Complement"
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                //* TO DO *\\
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
