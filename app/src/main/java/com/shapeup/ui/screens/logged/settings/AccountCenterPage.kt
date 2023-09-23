package com.shapeup.ui.screens.logged.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun AccountCenterPagePreview() {
    ShapeUpTheme {
        AccountCenterPage(
            navigator = Navigator()
        )
    }
}

@Composable
fun AccountCenterPage(
    navigator: Navigator
) {
    BackHandler {
        navigator.navigateBack
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
            text = "Account Center"
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
                Column {
                    Text(text = "Change Address")
                }

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
                Column {
                    Text(text = "Change e-mail")
                }

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
                Column {
                    Text(text = "Change mobile number")
                }

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
                Column {
                    Text(text = "Change birthday")
                }

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
                Column {
                    Text(text = "Change password")
                }

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
        Spacer(modifier = Modifier.height(80.dp))

        Column(

            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = "Sign Out",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(

                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error)


            ) {
                Text(
                    text = "Delete Account",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }


        }

    }
}
