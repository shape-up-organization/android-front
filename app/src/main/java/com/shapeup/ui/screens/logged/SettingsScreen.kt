package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.RowSettings
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock

@Composable
@Preview
fun SettingsScreenPreview() {
    ShapeUpTheme {
        SettingsScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun SettingsScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Header(
            navigateTo = { navigator.navigateBack() },
            text = stringResource(R.string.txt_settings_title)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            RowSettings(
                text = stringResource(R.string.txt_settings_account_center),
                screen = { navigator.navigate(Screen.AccountCenter) }
            )

            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            RowSettings(
                text =  stringResource(R.string.txt_settings_help),
                screen = { navigator.navigate(Screen.AccountCenter) }
            )

            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            RowSettings(text =  stringResource(R.string.txt_settings_about),
                screen = { navigator.navigate(Screen.AccountCenter) }
            )

            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )
        }
    }
}
