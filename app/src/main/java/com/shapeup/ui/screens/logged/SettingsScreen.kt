package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator

@Composable
@Preview
fun SettingsScreenPreview() {
    ShapeUpTheme {
        SettingsScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun SettingsScreen(
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
            text = "Settings"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(all = 32.dp)
            ) {

                Text(text = "Account center")


                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        contentDescription = stringResource(R.string.icon_arrow_forward),
                        painter = painterResource(id = Icon.ArrowForward.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(all = 32.dp)
            ) {

                Text(text = "Help")


                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        contentDescription = stringResource(R.string.icon_arrow_forward),
                        painter = painterResource(id = Icon.ArrowForward.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(all = 32.dp)
            ) {

                Text(text = "About")


                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        contentDescription = stringResource(R.string.icon_arrow_forward),
                        painter = painterResource(id = Icon.ArrowForward.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )
        }
    }
}
